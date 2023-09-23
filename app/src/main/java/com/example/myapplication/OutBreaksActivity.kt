package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent

class OutBreaksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_out_breaks)
        supportActionBar?.hide()
    }
    fun previous(view : View){
        startActivity(Intent(this,homeActivity::class.java))
        finish()
    }
}