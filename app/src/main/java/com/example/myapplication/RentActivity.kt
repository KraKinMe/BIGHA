package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View

class RentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_rent)
    }
    fun previous(view :View){
        startActivity(Intent(this,homeActivity::class.java))
        finish()
    }
    fun addyourmachineforrent(view: View){
        startActivity(Intent(this,addforrentActivity::class.java))

    }
}