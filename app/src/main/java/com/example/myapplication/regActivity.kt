package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.View
import android.widget.Button
import android.widget.EditText

class regActivity : AppCompatActivity() {
    private lateinit var Email:EditText
    private lateinit var Password:EditText
    private lateinit var submit:Button
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

        val intent =Intent(this,signUpActivity::class.java)
        intent.putExtra("N",Email.text.toString())
        intent.putExtra("P",Password.text.toString())
        startActivity(intent)
    }
}