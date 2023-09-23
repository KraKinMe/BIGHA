package com.example.myapplication

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast

class detectionActivity : AppCompatActivity() {
    public val REQUEST_CODE=22
    private lateinit var image: ImageView
    private lateinit var btngal: Button
    private lateinit var btncam : Button
    public val REQUEST_CODE_GALLERY=100;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detection)
        supportActionBar?.hide()
        Log.d("Data","Disease")
        image=findViewById(R.id.imageViewDisease)
        btncam=findViewById(R.id.CameraButton_id)
//            val btnpicture: Button=findViewById(R.id.CameraButton_id)
//            val image:ImageView=findViewById(R.id.imageViewDisease)

        btncam.setOnClickListener(View.OnClickListener() {

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent,REQUEST_CODE);
        });
        btngal=findViewById(R.id.GalleryButton)
        btngal.setOnClickListener(View.OnClickListener() {
            val galleryIntent= Intent(Intent.ACTION_PICK);
            galleryIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(galleryIntent,REQUEST_CODE_GALLERY)

        })





    }
    fun previous(view: View) {
        startActivity(Intent(this,homeActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( requestCode == REQUEST_CODE && resultCode== RESULT_OK) {
            val photo: Bitmap = data?.extras?.get("data") as Bitmap
            image.setImageBitmap(photo)


        }
        else
        {
            Toast.makeText(this,"cancelled", Toast.LENGTH_SHORT).show();
            super.onActivityResult(requestCode, resultCode, data)

        }
        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data

            // Set the selected image to the ImageView
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                image.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


}