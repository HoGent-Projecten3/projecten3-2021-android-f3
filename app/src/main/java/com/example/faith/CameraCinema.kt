package com.example.faith

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.android.volley.*
import kotlinx.android.synthetic.main.cinema_camera.*
import java.text.SimpleDateFormat
import java.util.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.Volley
import java.io.*
import kotlin.math.min

class CameraCinema : AppCompatActivity() {
    private var imageData: ByteArray? = null
    private val postURL: String = "http://192.168.1.37:45455/api/Cinema/imageFile"
    private val requestPermission = 100
    private val requestImageCapture = 1
    private val requestVideoCapture = 1
    private val videoMade = 2
    private val pictureTaken = 1
    lateinit var currentOutputPath: String
    private var lastCode = 0
    private val requestPhotoTaken = 1
    private var photoURI: Uri = Uri.EMPTY
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState);

        checkPermission()


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

            uploadImage()
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
            if(lastCode==1){
                createImageFile()
            }
            (lastCode==2)
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
    private fun createVideoFile():File{
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


    private fun uploadImage() {
        createImageData(photoURI)

        imageData ?: return
        val request = object : VolleyFileUploadRequest(
            Method.POST,
            postURL,
            Response.Listener {
                println("response is: $it")
            },
            Response.ErrorListener {
                println("error is: $it")
            }
        ) {
            override fun getByteData(): MutableMap<String, FileDataPart> {
                var params = HashMap<String, FileDataPart>()
                var fileNameToSend: String = randomName()
                params["imageFile"] = FileDataPart(fileNameToSend, imageData!!, "jpeg")
                return params
            }
        }
        Volley.newRequestQueue(this).add(request)
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
        if(lastCode==1){
            timeStamp += ".jpeg"
        }
        else{
            timeStamp+=".mp4"
        }

        return timeStamp

    }






    open class VolleyFileUploadRequest(
        method: Int,
        url: String,
        listener: Response.Listener<NetworkResponse>,
        errorListener: Response.ErrorListener
    ) : Request<NetworkResponse>(method, url, errorListener) {
        private var responseListener: Response.Listener<NetworkResponse>? = null

        init {
            this.responseListener = listener
        }

        private var headers: Map<String, String>? = null
        private val divider: String = "--"
        private val ending = "\r\n"
        private val boundary = "imageRequest${System.currentTimeMillis()}"


        override fun getHeaders(): MutableMap<String, String> =
            when (headers) {
                null -> super.getHeaders()
                else -> headers!!.toMutableMap()
            }

        override fun getBodyContentType() = "multipart/form-data;boundary=$boundary"


        @Throws(AuthFailureError::class)
        override fun getBody(): ByteArray {
            val byteArrayOutputStream = ByteArrayOutputStream()
            val dataOutputStream = DataOutputStream(byteArrayOutputStream)
            try {
                if (params != null && params.isNotEmpty()) {
                    processParams(dataOutputStream, params, paramsEncoding)
                }
                val data = getByteData() as? Map<String, FileDataPart>?
                if (data != null && data.isNotEmpty()) {
                    processData(dataOutputStream, data)
                }
                dataOutputStream.writeBytes(divider + boundary + divider + ending)
                return byteArrayOutputStream.toByteArray()

            } catch (e: IOException) {
                e.printStackTrace()
            }
            return super.getBody()
        }

        @Throws(AuthFailureError::class)
        open fun getByteData(): Map<String, Any>? {
            return null
        }

        override fun parseNetworkResponse(response: NetworkResponse): Response<NetworkResponse> {
            return try {
                Response.success(response, HttpHeaderParser.parseCacheHeaders(response))
            } catch (e: Exception) {
                Response.error(ParseError(e))
            }
        }

        override fun deliverResponse(response: NetworkResponse) {
            responseListener?.onResponse(response)
        }

        override fun deliverError(error: VolleyError) {
            errorListener?.onErrorResponse(error)
        }

        @Throws(IOException::class)
        private fun processParams(
            dataOutputStream: DataOutputStream,
            params: Map<String, String>,
            encoding: String
        ) {
            try {
                params.forEach {
                    dataOutputStream.writeBytes(divider + boundary + ending)
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"${it.key}\"$ending")
                    dataOutputStream.writeBytes(ending)
                    dataOutputStream.writeBytes(it.value + ending)
                }
            } catch (e: UnsupportedEncodingException) {
                throw RuntimeException(
                    "Unsupported encoding not supported: $encoding with error: ${e.message}",
                    e
                )
            }
        }

        @Throws(IOException::class)
        private fun processData(
            dataOutputStream: DataOutputStream,
            data: Map<String, FileDataPart>
        ) {
            data.forEach {
                val dataFile = it.value
                dataOutputStream.writeBytes("$divider$boundary$ending")
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"${it.key}\"; filename=\"${dataFile.fileName}\"$ending")
                if (dataFile.type != null && dataFile.type.trim().isNotEmpty()) {
                    dataOutputStream.writeBytes("Content-Type: ${dataFile.type}$ending")
                }
                dataOutputStream.writeBytes(ending)
                val fileInputStream = ByteArrayInputStream(dataFile.data)
                var bytesAvailable = fileInputStream.available()
                val maxBufferSize = 1024 * 1024
                var bufferSize = min(bytesAvailable, maxBufferSize)
                val buffer = ByteArray(bufferSize)
                var bytesRead = fileInputStream.read(buffer, 0, bufferSize)
                while (bytesRead > 0) {
                    dataOutputStream.write(buffer, 0, bufferSize)
                    bytesAvailable = fileInputStream.available()
                    bufferSize = min(bytesAvailable, maxBufferSize)
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize)
                }
                dataOutputStream.writeBytes(ending)
            }
        }
    }

    class FileDataPart(var fileName: String?, var data: ByteArray, var type: String)
}