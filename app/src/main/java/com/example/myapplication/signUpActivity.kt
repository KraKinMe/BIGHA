package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class signUpActivity : AppCompatActivity() {
    private lateinit var Email: EditText
    private lateinit var Password:EditText
    private lateinit var submit: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        submit = findViewById(R.id.submitlogin)

        submit.setOnClickListener{
            onhomepage()
        }
    }
    fun onRegisterNowClicked(view: View) {
        startActivity(Intent(this,regActivity::class.java))
        finish()
    }
    val email= arrayOf("7078128388","45164876847","2002")
    val pass = arrayOf("2004","2003","2002")


    fun onhomepage(){
        Email = findViewById(R.id.emailEditText)
        Password = findViewById(R.id.passEditText)
        val user = Email.text.toString()
        val pas =  Password.text.toString()
        var allow = false
        var index=0
        for (x in email){
            if(x==user && pass[index]==pas){
                allow=true
            }
            index++
        }
        if(allow){
            startActivity(Intent(this,homeActivity::class.java))
            finish()
        }
        else{
            Email.text.clear()
            Password.text.clear()
        }
    }

}