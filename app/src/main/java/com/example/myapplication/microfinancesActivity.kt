package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView

import android.widget.Toast

class  microfinancesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_microfinances)
        supportActionBar?.hide()


        val items = listOf(
            "Wheat",
            "Millet",
            "Rice",
            "Cotton",
            "SugarCane",
            "GroundNut",
            "Coffee",
            "Potato",
            "Mustard",
            "Onion"
        )
        val prices = listOf(
            "5",
            "6",
            "7",
            "8",
            "9",
            "10",
            "11",
            "12",
            "13",
            "14"
        )

        val autoComplete: AutoCompleteTextView = findViewById(R.id.auto_Complete)
        val adapter = ArrayAdapter(this, R.layout.list_dropdown, items)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view_, i, l ->
                val itemSelected = adapterView.getItemAtPosition(i)
                Toast.makeText(this, " Item : $itemSelected", Toast.LENGTH_SHORT).show()
                val msp = findViewById<TextView>(R.id.msp)
                msp.text=prices.get(i)
            }


    }
        fun previous(view: View) {
            startActivity(Intent(this, home2Activity::class.java))
            finish()
        }

}