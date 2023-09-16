package com.example.myapplication



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor=ContextCompat.getColor(this,R.color.brown)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this,signUpActivity::class.java))
            finish()
        },3000)
    }
}