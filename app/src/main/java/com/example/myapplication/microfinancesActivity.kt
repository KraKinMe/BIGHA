package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class  microfinancesActivity : AppCompatActivity() {
    private lateinit var CropWeight:EditText
    private lateinit var Price:EditText
    private lateinit var database: DatabaseReference
    private lateinit var database1: DatabaseReference
    private lateinit var sharedPrefUserName: SharedPreferences
    private lateinit var user: String
    private lateinit var selectedCrop: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_microfinances)
        supportActionBar?.hide()
        sharedPrefUserName = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        user = sharedPrefUserName.getString("User", "John Doe") ?: "."

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
                val itemSelected = adapterView.getItemAtPosition(i) as String
                selectedCrop = itemSelected
                Toast.makeText(this, " Item : $itemSelected", Toast.LENGTH_SHORT).show()
                val msp = findViewById<TextView>(R.id.msp)
                msp.text=prices.get(i)
            }

    }

    fun Submit(view: View){
        var FieldSize:Int=0
        CropWeight=findViewById(R.id.CropWeight)
        Price=findViewById(R.id.Price)

        Log.d("microfinancesActivity", "Starting Submit function")

        database1= FirebaseDatabase.getInstance().getReference("VerifiedFarmers")

        database1.child(user).get().addOnSuccessListener {
            if(it.exists()){
                var FieldSizeStr = it.child("farmArea").value
                if (FieldSizeStr is Long) {
                    FieldSize = FieldSizeStr.toInt()
                } else if (FieldSizeStr is String) {
                    FieldSize = FieldSizeStr.toIntOrNull() ?: 0
                }
                var MaxCrop=MC(FieldSize,selectedCrop)

                var CW: Int = 0
                val cropWeightText = CropWeight.text.toString()
                if (cropWeightText.isNotEmpty()) {
                    CW = cropWeightText.toInt()
                }

                if(CW>MaxCrop){
                    Log.d("microfinancesActivity", "FieldSize: $FieldSize")
                    Log.d("microfinancesActivity", "selectedCrop: $selectedCrop")
                    Log.d("microfinancesActivity", "MaxCrop: $MaxCrop")
                    Toast.makeText(this,"Aukaat: Max=$MaxCrop",Toast.LENGTH_SHORT).show()
                }
                else{
                    database = FirebaseDatabase.getInstance().getReference("MFSellers")
                    val MFSeller=MFSeller(selectedCrop,CropWeight.text.toString(),Price.text.toString())
                    Log.d("microfinancesActivity", "Saving data to Firebase: $MFSeller")

                    val mfRef=database.child(user).push()



                    mfRef.setValue(MFSeller).addOnSuccessListener {
                        Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
                        Log.d("microfinancesActivity", "Data added to Firebase successfully")
                        val intent =Intent(this, microfinancesActivity::class.java)
                        startActivity(intent)
                        finish()

                    }.addOnCanceledListener {
                        Log.d("microfinancesActivity", "Failed to add data to Firebase")
                        Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }.addOnCanceledListener {
            Log.d("microfinancesActivity", "Failed to retrieve data from Firebase")
            Toast.makeText(this,"Network Error",Toast.LENGTH_SHORT).show()
        }

    }

    fun MC(FS: Int?, SC: String?): Int {
        return when (SC) {
            "Wheat" -> FS?.times(1) ?: 0
            "Millet" -> FS?.times(2) ?: 0
            "Rice" -> FS?.times(3) ?: 0
            "Cotton" -> FS?.times(4) ?: 0
            "SugarCane" -> FS?.times(5) ?: 0
            "GroundNut" -> FS?.times(6) ?: 0
            "Coffee" -> FS?.times(7) ?: 0
            "Potato" -> FS?.times(8) ?: 0
            "Mustard" -> FS?.times(9) ?: 0
            "Onion" -> FS?.times(10) ?: 0
            else -> 0
        }
    }
    fun previous(view: View) {
        startActivity(Intent(this, home2Activity::class.java))
        finish()
    }
    fun RedirectStocks(view:View){

        startActivity(Intent(this, StocksActivity::class.java))
    }
}