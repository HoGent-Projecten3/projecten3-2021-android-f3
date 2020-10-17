package com.example.faith

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Message
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.cinema_camera.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.Response

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CameraCinema : AppCompatActivity() {
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
    private lateinit var service: APIInterface
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState);

        checkPermission()

        val retrofit = Retrofit.Builder().baseUrl("http://192.168.1.37:45455/api/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        service = retrofit.create(APIInterface::class.java)




        setContentView(R.layout.cinema_camera)

        btCapturePhoto.setOnClickListener {
            openCameraPicture()
        }
        btOpenGallery.setOnClickListener {
            openGallery()
        }
        btCaptureVideo.setOnClickListener {
            openCameraVideo()
        }
        sendButton.setOnClickListener {
            upload()

        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1
            );
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
            );
        }

        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 1);
        }
    }

    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }

    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
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
                this,
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
            takePictureIntent.resolveActivity(packageManager)?.also {
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
            intent.resolveActivity(packageManager)?.also {
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
        lastCode=0
        Intent(Intent.ACTION_PICK).also { intent ->

            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {


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
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
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
        if (resultCode == RESULT_OK) {
            if (requestCode == requestImageCapture && lastCode == pictureTaken) {
                ivImage.setImageURI(photoURI)
                ivImage.visibility = View.VISIBLE


        } else if (requestCode == requestPermission && resultCode == RESULT_OK) {
            val uri = theIntent?.data
            if (uri != null) {
                photoURI = uri
            }
            ivImage.setImageURI(uri)
            ivImage.visibility = View.VISIBLE
            //todo de foto niet alleen weergeven maar ook opslaan in db
        } else if (requestCode == requestVideoCapture && lastCode == videoMade) {

                var videoUri = theIntent?.data
                videoView.visibility = View.VISIBLE
                videoView.setVideoURI(videoUri)
                videoView.start()
            }
        }
    }


    @Throws(IOException::class)
    private fun createImageData(uri: Uri) {
        val inputStream = contentResolver.openInputStream(uri)
        inputStream?.buffered()?.use {

            imageData = it.readBytes()

        }
    }

    private fun randomName(): String {
        var timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        if (lastCode == 1 || lastCode==0) {
            timeStamp += ".jpeg"
        } else {
            timeStamp += ".mp4"
        }

        return timeStamp

    }

    private fun upload() {
        createImageData(photoURI)
        var kindOfMedia = ""
        if (lastCode == 1 ||lastCode==0) {
            kindOfMedia = "image/*"
        } else {
            kindOfMedia = "video/*"
        }
        val part = MultipartBody.Part.createFormData(
            "imageFile", randomName(), RequestBody.create(
                MediaType.get(kindOfMedia),
                imageData
            )
        )
        var call: Call<Message> = service.uploadMedia("Cinema/imageFile", part)
        call.enqueue(object : Callback<Message?> {
            override fun onFailure(call: Call<Message?>, t: Throwable) {}
            override fun onResponse(call: Call<Message?>, response: retrofit2.Response<Message?>) {
                TODO("Not yet implemented")
            }
        })


    }

}












