package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.View

class verifylockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verifylock)
    }
    fun GoToVerification(view:View){
        val intent = Intent(this, verificationActivity::class.java)
        intent.putExtra("key_name", "Hello from MainActivity!")
        startActivity(intent)
        finish()
    }
}