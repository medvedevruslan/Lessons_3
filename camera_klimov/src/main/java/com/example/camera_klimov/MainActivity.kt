package com.example.camera_klimov

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private val REQUEST_TAKE_PHOTO = 0
    private lateinit var imageFilePath: String
    private lateinit var imageUri: Uri

    private lateinit var imageView: ImageView

    @SuppressLint("QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)

        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            try {
                val photoFile = createImageFile()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (cameraIntent.resolveActivity(packageManager) != null) {
                    val authorities = BuildConfig.APPLICATION_ID + ".provider"
                    imageUri = FileProvider.getUriForFile(this, authorities, photoFile)
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
                    startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO)
                }
            } catch (e: Exception) {
                Log.d("DEVELOPER", "Could not create file!: $e")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_TAKE_PHOTO -> {
                if (resultCode == RESULT_OK) {
                    /* миниатюра
                    if (data != null) {
                        imageView.setImageBitmap(data.extras?.get("data") as Bitmap)*/

                    // полноразмерное изображение
                    imageView.setImageURI(imageUri)
                    println("Image URI: $imageUri File Path:$imageFilePath")
                    Log.d("developer", "Image URI: $imageUri File Path:$imageFilePath")
                }
            }
            else -> {
                Toast.makeText(this, "Wrong request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Throws(IOException::class)
    fun createImageFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val imageFileName = "IMAGE_$timeStamp "
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        // val storageDir = filesDir
        if (!storageDir?.exists()!!) storageDir.mkdirs()
        val imageFile = createTempFile(imageFileName, /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */)

        imageFilePath = imageFile.absolutePath
        Log.d("deloper", "Image Path: $imageFilePath")

        return imageFile
    }
}