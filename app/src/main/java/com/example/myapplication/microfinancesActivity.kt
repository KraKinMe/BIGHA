package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class microfinancesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_microfinances)
        supportActionBar?.hide()
    }
    fun previous(view : View){
        startActivity(Intent(this,homeActivity::class.java))
        finish()
    }
}