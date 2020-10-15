package com.example.faith

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_MEDIA_SCANNER_SCAN_FILE
import android.content.pm.PackageManager
import android.graphics.Bitmap
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
import java.lang.Double.min
import kotlin.math.min

class CameraCinema : AppCompatActivity() {
    private final val REQUEST_PERMISSION = 100
    private final val REQUEST_IMAGE_CAPTURE = 1
    private final val REQUEST_VIDEO_CAPTURE = 1
    private final val VIDEO_MADE = 2
    private final val PICTURE_TAKEN = 1
    lateinit var currentPhotoPath: String
    var last_code = 0
    val REQUEST_TAKE_PHOTO = 1
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
                REQUEST_PERMISSION
            )
        }
    }
    var photoURI:Uri=Uri.EMPTY
    private fun openCameraPicture() {
        hidePreviews()
        last_code=1

        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {


                      photoURI= FileProvider.getUriForFile(
                        this,
                        "com.example.faith.fileprovider",
                        it
                    )

                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)





                }
            }
        }
    }

    private fun openCameraVideo() {
        hidePreviews()
        last_code = 2
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_VIDEO_CAPTURE)
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
                startActivityForResult(intent, REQUEST_PERMISSION)
            }
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
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, theIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, theIntent)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE && last_code == PICTURE_TAKEN) {
                ivImage.setImageURI (photoURI)
                ivImage.visibility = View.VISIBLE


            } else if (requestCode == REQUEST_PERMISSION && resultCode == RESULT_OK) {
                val uri = theIntent?.data
                ivImage.setImageURI(uri)
                ivImage.visibility = View.VISIBLE
                //todo de foto niet alleen weergeven maar ook opslaan in db
            } else if (requestCode == REQUEST_VIDEO_CAPTURE && last_code == VIDEO_MADE) {

                var videoUri = theIntent?.data
                videoView.visibility = View.VISIBLE
                videoView.setVideoURI(videoUri)
                videoView.start()

            }
        }
    }
    private fun galleryAddPic() {
        Intent(ACTION_MEDIA_SCANNER_SCAN_FILE).also { mediaScanIntent ->
            val f = File(currentPhotoPath)
            mediaScanIntent.data = Uri.fromFile(f)
            sendBroadcast(mediaScanIntent)
        }
    }



    private var imageData: ByteArray? = null
    //private val postURL: String = "https://ptsv2.com/t/rjlgd-1602675094/post"
   // private val postURL: String = "https://f3backend-dev-as.azurewebsites.net/api/Cinema"
    private val postURL: String = "http://192.168.1.37:45455/api/Cinema/imageFile"
    //private val postURL:String = "https://5f88144d49ccbb0016178002.mockapi.io/foto"
            private fun uploadImage() {
        createImageData(photoURI)

        imageData?: return
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
                params["imageFile"] = FileDataPart("image", imageData!!, "jpeg")
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

    open class VolleyFileUploadRequest(
            method: Int,
            url: String,
            listener: Response.Listener<NetworkResponse>,
            errorListener: Response.ErrorListener) : Request<NetworkResponse>(method, url, errorListener) {
        private var responseListener: Response.Listener<NetworkResponse>? = null
        init {
            this.responseListener = listener
        }

        private var headers: Map<String, String>? = null
        private val divider: String = "--"
        private val ending = "\r\n"
        private val boundary = "imageRequest${System.currentTimeMillis()}"


        override fun getHeaders(): MutableMap<String, String> =
                when(headers) {
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
        private fun processParams(dataOutputStream: DataOutputStream, params: Map<String, String>, encoding: String) {
            try {
                params.forEach {
                    dataOutputStream.writeBytes(divider + boundary + ending)
                    dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"${it.key}\"$ending")
                    dataOutputStream.writeBytes(ending)
                    dataOutputStream.writeBytes(it.value + ending)
                }
            } catch (e: UnsupportedEncodingException) {
                throw RuntimeException("Unsupported encoding not supported: $encoding with error: ${e.message}", e)
            }
        }

        @Throws(IOException::class)
        private fun processData(dataOutputStream: DataOutputStream, data: Map<String, FileDataPart>) {
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