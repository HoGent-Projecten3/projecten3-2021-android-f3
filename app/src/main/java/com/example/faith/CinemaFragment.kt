package com.example.faith

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Message
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.faith.R.layout.fragment_cinema
import com.example.faith.databinding.FragmentCinemaBinding
import com.example.faith.viewmodels.CinemaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_cinema.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.jvm.Throws
/**
 * @author Remi Mestdagh
 */
@AndroidEntryPoint
class CinemaFragment : Fragment() {
    private val viewModel: CinemaViewModel by viewModels()
    private var imageData: ByteArray? = null
    private val requestPermission = 100
    private val requestImageCapture = 1
    private val requestVideoCapture = 1
    private val videoMade = 2
    private val pictureTaken = 1
    lateinit var currentOutputPath: String
    private var lastCode = 0
    private val requestPhotoTaken = 1
    private var photoURI: Uri = Uri.EMPTY
    private val Fragment.packageManager get() = activity?.packageManager
    private val Fragment.contentResolver get() = activity?.contentResolver
    private var videoURI: Uri = Uri.EMPTY

    /**
     * Method called upon starting view creation
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        /* Enabling data binding for fragments. slightly different because no immediate acces to root Activity */
        val binding = DataBindingUtil.inflate<FragmentCinemaBinding>(
            inflater,
            fragment_cinema,
            container,
            false
        )
        checkPermission()
        binding.btCapturePhoto.setOnClickListener {
            openCameraPicture()
        }
        binding.btOpenGallery.setOnClickListener {
            openGallery()
        }
        binding.btCaptureVideo.setOnClickListener {
            openCameraVideo()
        }
        binding.sendButton.setOnClickListener {
            upload()
        }

        return binding.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            photoURI = Uri.parse(savedInstanceState.getString("photoURI"))
            videoURI = Uri.parse(savedInstanceState.getString("videoURI"))
        }
        if (photoURI == Uri.EMPTY && videoURI != Uri.EMPTY) {
            videoView.setVideoURI(videoURI)
            videoView.visibility = View.VISIBLE
            videoView.start()
        } else if (photoURI != Uri.EMPTY) {
            ivImage.setImageURI(photoURI)
            ivImage.visibility = View.VISIBLE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("videoURI", videoURI.toString())
        outState.putString("photoURI", photoURI.toString())
        super.onSaveInstanceState(outState)
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                1
            )
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA), 1)
        }
    }

    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.CAMERA),
                requestPermission
            )
        }
    }

    private fun createFile() {
        val photoFile: File? = try {
            if (lastCode == 1) {
                createImageFile()
            }
            (lastCode == 2)
            createVideoFile()
        } catch (ex: IOException) {
            null
        }
        // Continue only if the File was successfully created
        photoFile?.also {

            photoURI = FileProvider.getUriForFile(
                requireContext(),
                "com.example.faith.fileprovider",
                it
            )
        }
    }

    private fun openCameraPicture() {
        hidePreviews()
        lastCode = 1

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager!!)?.also {
                // Create the File where the photo should go

                createFile()

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, requestPhotoTaken)
            }
        }
    }

    private fun openCameraVideo() {
        hidePreviews()
        lastCode = 2
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager!!)?.also {
                createFile()
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                startActivityForResult(intent, requestVideoCapture)
            }
        }
    }

    private fun hidePreviews() {
        ivImage.visibility = View.GONE
        videoView.visibility = View.GONE
    }

    private fun openGallery() {
        hidePreviews()
        lastCode = 0
        Intent(Intent.ACTION_PICK).also { intent ->

            intent.type = "image/*"
            intent.resolveActivity(packageManager!!)?.also {

                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {

                    startActivityForResult(intent, requestPermission)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createVideoFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "MP4${timeStamp}_",
            ".mp4",
            storageDir
        ).apply {
            currentOutputPath = absolutePath
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? =
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentOutputPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, theIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, theIntent)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            if (requestCode == requestImageCapture && lastCode == pictureTaken) {
                ivImage.setImageURI(photoURI)
                ivImage.visibility = View.VISIBLE
            } else if (requestCode == requestPermission && resultCode == AppCompatActivity.RESULT_OK) {
                val uri = theIntent?.data
                if (uri != null) {
                    photoURI = uri
                }
                ivImage.setImageURI(uri)
                ivImage.visibility = View.VISIBLE
                // todo de foto niet alleen weergeven maar ook opslaan in db
            } else if (requestCode == requestVideoCapture && lastCode == videoMade) {
                photoURI = Uri.EMPTY

                videoURI = theIntent?.data!!
                videoView.visibility = View.VISIBLE
                videoView.setVideoURI(videoURI)
                videoView.start()
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver!!.openInputStream(uri)
        inputStream?.buffered()?.use {

            imageData = it.readBytes()
        }
    }

    private fun randomName(): String {
        var timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        if (lastCode == 1 || lastCode == 0) {
            timeStamp += ".jpeg"
        } else {
            timeStamp += ".mp4"
        }

        return timeStamp
    }

    private fun upload() {
        if (photoURI != Uri.EMPTY) {
            createImageData(photoURI)
        }

        var kindOfMedia = ""
        if (lastCode == 1 || lastCode == 0) {
            kindOfMedia = "image/*"
        } else {
            kindOfMedia = "video/*"
        }
        val part = imageData?.let {
            RequestBody.create(
                kindOfMedia.toMediaType(),
                it
            )
        }?.let {
            MultipartBody.Part.createFormData(
                "imageFile",
                randomName(),
                it
            )
        }
        var call: Call<Message>? = part?.let { viewModel.uploadMedia(it, txfBericht.text?.toString()) }
        call!!.enqueue(
            object : Callback<Message?> {
                override fun onFailure(call: Call<Message?>, t: Throwable) {}
                override fun onResponse(call: Call<Message?>, response: retrofit2.Response<Message?>) {
                }
            }
        )
    }
}
