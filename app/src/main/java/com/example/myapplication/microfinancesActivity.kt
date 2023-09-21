package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView

import android.widget.Toast

class microfinancesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_microfinances)
        supportActionBar?.hide()


        val items = listOf("Item 1", "Item 2", "Item 3", "Item 4","item5","item6","Item 1", "Item 2", "Item 3", "Item 4","item5","Item 1", "Item 2", "Item 3", "Item 4","item5")
        val autoComplete: AutoCompleteTextView = findViewById(R.id.auto_Complete)
        val adapter = ArrayAdapter(this, R.layout.list_dropdown, items)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener = AdapterView.OnItemClickListener {
                adapterView, view_, i, l ->
                val itemSelected = adapterView.getItemAtPosition(i)
                Toast.makeText(this," Item : $itemSelected", Toast.LENGTH_SHORT).show()
            }



        fun previous(view: View) {
            startActivity(Intent(this, homeActivity::class.java))
            finish()
        }
    }
}