package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class verificationActivity : AppCompatActivity() {
    private lateinit var FarmArea:EditText
    private lateinit var GovtID:EditText
    private lateinit var Verify:Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        supportActionBar?.hide()
        Verify = findViewById(R.id.VerifyMe)


        FarmArea = findViewById(R.id.FarmArea)
        GovtID = findViewById(R.id.GovtID)

        Verify.setOnClickListener {
            Toast.makeText(this,"Sending ...",Toast.LENGTH_SHORT).show()
            val FarmArea=FarmArea.text.toString()
            val GovtID=GovtID.text.toString()
            val sharedPref=getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val savedUserName=sharedPref.getString("User","ghfhgf")?:"."

            database = FirebaseDatabase.getInstance().getReference("VerifiedFarmers")
            val VerifiedFarmers=VerifiedFarmers(FarmArea,GovtID)

            database.child(savedUserName).setValue(VerifiedFarmers).addOnSuccessListener {
                Toast.makeText(this,"You Are Verified",Toast.LENGTH_SHORT).show()
            }.addOnCanceledListener {
                Toast.makeText(this,"Kuch to Gadbad Hai Daya",Toast.LENGTH_SHORT).show()
            }
        }

    }

    fun previous(view : View){
        startActivity(Intent(this,home2Activity::class.java))
        finish()
    }

}