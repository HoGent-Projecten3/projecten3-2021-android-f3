package com.example.faith
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.cinema_camera.*


class CameraCinema : AppCompatActivity() {
    private final val REQUEST_PERMISSION = 100
    private final val REQUEST_IMAGE_CAPTURE = 1
    private final val REQUEST_VIDEO_CAPTURE = 1
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
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_PERMISSION)
        }
    }
    private fun openCameraPicture() {
        last_code=1
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
    private fun openCameraVideo(){
        last_code=2
        Intent(MediaStore.ACTION_VIDEO_CAPTURE).also { intent ->
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_VIDEO_CAPTURE)
            }
        }

    }
    private fun openGallery() {
        Intent(Intent.ACTION_PICK).also { intent ->
            intent.type = "image/*"
            intent.resolveActivity(packageManager)?.also {
                startActivityForResult(intent, REQUEST_PERMISSION)
            }
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE &&last_code==1) {
                val bitmap = data?.extras?.get("data") as Bitmap
                ivImage.setImageBitmap(bitmap)
            }
            else if (requestCode == REQUEST_PERMISSION && resultCode== RESULT_OK) {
                val uri = data?.getData()
                ivImage.setImageURI(uri)
                //todo de foto niet alleen weergeven maar ook opslaan in db
            }
            else if (requestCode == REQUEST_VIDEO_CAPTURE &&last_code==2) {
                val bitmap = data?.extras?.get("data") as Bitmap
                ivImage.setImageBitmap(bitmap)
            }
        }
    }

}