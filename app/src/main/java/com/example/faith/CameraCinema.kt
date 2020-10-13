package com.example.faith
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.cinema_camera.*
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class CameraCinema : AppCompatActivity() {
    private final val REQUEST_PERMISSION = 100
    private final val REQUEST_IMAGE_CAPTURE = 1
    private final val REQUEST_VIDEO_CAPTURE = 1
    private final val VIDEO_MADE = 2
    private final val PICTURE_TAKEN = 1
    public var last_code = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cinema_camera)
        btCapturePhoto.setOnClickListener {
            openCameraPicture()
        }
        btOpenGallery.setOnClickListener {
            openGallery()
        }
        btCaptureVideo.setOnClickListener{
            openCameraVideo()
        }
    }
    override fun onResume() {
        super.onResume()
        checkCameraPermission()
    }
    private fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION
            )
        }
    }
    private fun openCameraPicture() {
        hidePreviews()
        last_code=1
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    private fun openCameraVideo(){
        hidePreviews()
        last_code=2
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_VIDEO_CAPTURE)
            }
        }

    }
    private fun hidePreviews(){
        ivImage.visibility = View.GONE
        videoView.visibility=View.GONE

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




    override fun onActivityResult(requestCode: Int, resultCode: Int, intent1: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent1)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE &&last_code==PICTURE_TAKEN) {
                val bitmap = intent1?.extras?.get("data") as Bitmap
                ivImage.setImageBitmap(bitmap)
                ivImage.visibility = View.VISIBLE

            }
            else if (requestCode == REQUEST_PERMISSION && resultCode== RESULT_OK) {
                val uri = intent1?.data
                ivImage.setImageURI(uri)
                ivImage.visibility = View.VISIBLE
                //todo de foto niet alleen weergeven maar ook opslaan in db
            }
            else if (requestCode == REQUEST_VIDEO_CAPTURE &&last_code==VIDEO_MADE) {

                var videoUri = intent1?.data
                videoView.visibility=View.VISIBLE
               videoView.setVideoURI(videoUri)

                videoView.start()

            }
        }
    }

}