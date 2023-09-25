package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.ByteArrayOutputStream

class verificationActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_PICK=1
    private lateinit var farmImageView: ImageView
    private lateinit var selectImageButton: Button
    private lateinit var FarmArea:EditText
    private lateinit var GovtID:EditText
    private lateinit var Verify:Button
    private lateinit var database: DatabaseReference
    var sImage:String?=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        supportActionBar?.hide()
        Verify = findViewById(R.id.VerifyMe)
        farmImageView =findViewById(R.id.farmImageView)
        selectImageButton = findViewById(R.id.selectImageButton)

        selectImageButton.setOnClickListener {
            // Launch the image picker when the button is clicked
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_IMAGE_PICK)
//            Toast.makeText(this,"Image selected",Toast.LENGTH_SHORT).show()
        }

        FarmArea = findViewById(R.id.FarmArea)
        GovtID = findViewById(R.id.GovtID)

        Verify.setOnClickListener {
            Toast.makeText(this,"Sending ...",Toast.LENGTH_SHORT).show()
            val FarmArea=FarmArea.text.toString()
            val GovtID=GovtID.text.toString()
            val sharedPref=getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val savedUserName=sharedPref.getString("User","ghfhgf")?:"."

            database = FirebaseDatabase.getInstance().getReference("VerifiedFarmers")
            val VerifiedFarmers=VerifiedFarmers(FarmArea,sImage,GovtID)
            var check = false

            //     Toast.makeText(this,"Agla toast",Toast.LENGTH_SHORT).show()
            database.child(savedUserName).setValue(VerifiedFarmers).addOnSuccessListener {
                Toast.makeText(this,savedUserName,Toast.LENGTH_SHORT).show()
                check=true

            }.addOnCanceledListener {
                Toast.makeText(this,"Kuch to Gadbad Hai Daya",Toast.LENGTH_SHORT).show()
                check=false
            }
        }

    }

    fun previous(view : View){
        startActivity(Intent(this,homeActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data

            // Set the selected image to the ImageView
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                farmImageView.setImageBitmap(bitmap)
                val inputStream = contentResolver.openInputStream(selectedImageUri!!)
                val myBitmap = BitmapFactory.decodeStream(inputStream)
                var stream = ByteArrayOutputStream()
                myBitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
                var bytes = stream.toByteArray()
                sImage= Base64.encodeToString(bytes,Base64.DEFAULT)
                Toast.makeText(this,"Image Selected",Toast.LENGTH_SHORT).show()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}