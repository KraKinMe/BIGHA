package com.example.myapplication

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class signUpActivity : AppCompatActivity() {
    private lateinit var Email: EditText
    private lateinit var Password:EditText
    private lateinit var submit: Button
    private lateinit var database: DatabaseReference
    private lateinit var Name:EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar?.hide()
        submit = findViewById(R.id.submitlogin)

        submit.setOnClickListener{
            onhomepage()
        }
    }
    fun onRegisterNowClicked(view: View) {
        startActivity(Intent(this,regActivity::class.java))

    }

    fun onhomepage(){
        Email = findViewById(R.id.emailEditText)
        Password = findViewById(R.id.passEditText)

        val user = Email.text.toString()
        val pas =  Password.text.toString()
        var allow = false
        database=FirebaseDatabase.getInstance().getReference("Users")
        database.child(user).get().addOnSuccessListener {
            if(it.exists()){
                val StoredPass=it.child("password").value
                if(pas.equals(StoredPass)){
                    allow=true
                    val sharedPrefUserName = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                    val editor = sharedPrefUserName.edit()
                    editor.putString("User",user)
                    editor.apply()
                    startActivity(Intent(this,home2Activity::class.java))
                    finish()
                    /// Get function is asynchronous , dont change anything here
                }else{
                    Toast.makeText(this,"Wrong Password / Username",Toast.LENGTH_SHORT).show()

                }
            }
        }.addOnCanceledListener {
            Toast.makeText(this,"Network Error",Toast.LENGTH_SHORT).show()
        }
        if(allow==true){
            startActivity(Intent(this,home2Activity::class.java))
            finish()
        }else{
            Email.text.clear()
            Password.text.clear()
        }
    }

}