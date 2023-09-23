package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast

class mpsellActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mpsell)
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
        val SellCropName: AutoCompleteTextView = findViewById(R.id.SellCropName)
        val adapter = ArrayAdapter(this, R.layout.list_dropdown, items)
        SellCropName.setAdapter(adapter)

        SellCropName.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view_, i, l ->
                val itemSelected = adapterView.getItemAtPosition(i)
                Toast.makeText(this, " Item : $itemSelected", Toast.LENGTH_SHORT).show()
            }
    }
}