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


class addforrentActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_PICK=1
    private lateinit var toolImageView: ImageView
    private lateinit var selectImageButton: Button
    private lateinit var Desc:EditText
    private lateinit var Name:EditText
    private lateinit var submit:Button
    private lateinit var database: DatabaseReference
    private lateinit var Price:EditText
    private lateinit var Location:EditText
    var sImage:String? =""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_addforrent)
        toolImageView = findViewById(R.id.toolImageView)
        selectImageButton = findViewById(R.id.selectImageButton)

        selectImageButton.setOnClickListener {
            // Launch the image picker when the button is clicked
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, REQUEST_IMAGE_PICK)

        }
        Desc = findViewById(R.id.Descoftool)
        Name = findViewById(R.id.ToolName)
        submit = findViewById(R.id.submitBtn)
        Price = findViewById(R.id.priceoftool)
        Location = findViewById(R.id.Locationtool)
        submit.setOnClickListener {
            Toast.makeText(this,"Sending ...",Toast.LENGTH_SHORT).show()
            val descTxt=Desc.text.toString()
            val nameTxt=Name.text.toString()
            var priceTxt=Price.text.toString()
            var locationTxt=Location.text.toString()
            database = FirebaseDatabase.getInstance().getReference("Machines")
            val Machines=Machines(nameTxt,descTxt,sImage,priceTxt,locationTxt)
            val sharedPref=getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val savedUserName=sharedPref.getString("User","def")?:nameTxt

       //     Toast.makeText(this,"Agla toast",Toast.LENGTH_SHORT).show()
            val machineEntryRef = database.child(savedUserName).push()
            machineEntryRef.setValue(Machines).addOnSuccessListener {
                Toast.makeText(this, "Your tool is up for Rent", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this,RentActivity::class.java))
            }.addOnCanceledListener {
                Toast.makeText(this, "Not done Bro", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun previous(view : View){
        startActivity(Intent(this,RentActivity::class.java))
        finish()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data

            // Set the selected image to the ImageView
            try {
                val bitmap: Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                toolImageView.setImageBitmap(bitmap)
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