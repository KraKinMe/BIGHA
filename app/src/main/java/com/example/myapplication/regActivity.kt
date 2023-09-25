package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlin.math.sign

class regActivity : AppCompatActivity() {
    private lateinit var Email:EditText
    private lateinit var Password:EditText
    private lateinit var submit:Button
    private lateinit var database : DatabaseReference
    private lateinit var Name : EditText
    private lateinit var CPassword:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg)
        supportActionBar?.hide()
        submit=findViewById(R.id.submitsignup)
        submit.setOnClickListener{
            addVal()
        }
    }
    fun previous(view: View) {
        startActivity(Intent(this,signUpActivity::class.java))
        finish()
    }
    fun addVal(){
        Email=findViewById(R.id.MobileEditText)
        Password=findViewById(R.id.passs)
        Name = findViewById(R.id.nameEditText)
        CPassword=findViewById(R.id.cpasss)

        val user_id=Email.text.toString()
        val name = Name.text.toString()
        val pass = Password.text.toString()
        val cpass = CPassword.text.toString()

        if(user_id.length!=10 || !(user_id[0]=='9' || user_id[0]=='8' || user_id[0]=='7' || user_id[0]=='6')){
            Toast.makeText(this,"Enter Correct Mobile No.",Toast.LENGTH_SHORT).show()
        }
        else if(pass!=cpass){
            Toast.makeText(this,"Password Does Not Match",Toast.LENGTH_SHORT).show()
        }
        else{
            database = FirebaseDatabase.getInstance().getReference("Users")
            val User=User(name,pass,user_id)
            database.child(user_id).setValue(User).addOnSuccessListener {
                val intent =Intent(this, signUpActivity::class.java)
                finish()
            }.addOnCanceledListener {
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }
}