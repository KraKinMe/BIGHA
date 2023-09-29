package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
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
    private lateinit var MSP: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_microfinances)
        supportActionBar?.hide()
        sharedPrefUserName = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        user = sharedPrefUserName.getString("User", "John Doe") ?: "."

        val items = listOf(
            "Wheat",
            "Millet",
            "Paddy",
            "Cotton-Medium Staple",
            "Cotton-Long Staple",
            "GroundNut",
            "Tur(Arhar)",
            "Moong",
            "Mustard",
            "Urad"
        )
        val prices = listOf(
            "20",
            "23",
            "20",
            "60",
            "63",
            "58",
            "66",
            "77",
            "50",
            "66"
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
                MSP=msp.text.toString()
            }

    }

    fun Submit(view: View) {
        var FieldSize: Int = 0
        CropWeight = findViewById(R.id.CropWeight)
        Price = findViewById(R.id.Price)

        Log.d("microfinancesActivity", "Starting Submit function")

        database1 = FirebaseDatabase.getInstance().getReference("VerifiedFarmers")

        database1.child(user).get().addOnSuccessListener { it ->
            if (it.exists()) {
                var FieldSizeStr = it.child("farmArea").value
                FieldSize = when (FieldSizeStr) {
                    is Long -> FieldSizeStr.toInt()
                    is String -> FieldSizeStr.toIntOrNull() ?: 0
                    else -> 0
                }

                var MaxCrop = MC(FieldSize, selectedCrop)

                var CW: Int = 0
                val cropWeightText = CropWeight.text.toString()
                if (cropWeightText.isNotEmpty()) {
                    CW = cropWeightText.toInt()
                }

                Log.d("microfinancesActivity", "FieldSize: $FieldSize")
                Log.d("microfinancesActivity", "selectedCrop: $selectedCrop")
                Log.d("microfinancesActivity", "CW: $CW")
                Log.d("microfinancesActivity", "MaxCrop: $MaxCrop")

                if (CW > MaxCrop) {
                    Toast.makeText(this, "Aukaat: Max=$MaxCrop", Toast.LENGTH_SHORT).show()
                } else {
                    Log.d("microfinancesActivity", "ShowConfirmMustStart")
                    showConfirm(CW, MSP.toInt(), Price.text.toString().toInt())
                }
            }
        }.addOnCanceledListener {
            Log.d("microfinancesActivity", "Failed to retrieve data from Firebase")
            Toast.makeText(this, "Network Error", Toast.LENGTH_SHORT).show()
        }
    }


    fun MC(FS: Int?, SC: String?): Int {
        return when (SC) {
            "Wheat" -> FS?.times(1600) ?: 0
            "Millet" -> FS?.times(1100) ?: 0
            "Paddy" -> FS?.times(2600) ?: 0
            "Cotton-Medium Staple" -> FS?.times(1400) ?: 0
            "Cotton-Long Staple" -> FS?.times(600) ?: 0
            "GroundNut" -> FS?.times(1600) ?: 0
            "Tur(Arhar)" -> FS?.times(370) ?: 0
            "Moong" -> FS?.times(370) ?: 0
            "Mustard" -> FS?.times(700) ?: 0
            "Urad" -> FS?.times(1000) ?: 0
            else -> 0
        }
    }


    fun showConfirm(CW:Int?,MSP:Int?,Price:Int?){
        Log.d("microfinancesActivity", "ShowConfirmStarted")
        val m= (CW!! * MSP!!).toString()
        val i=(CW* Price!!).toString()

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_mf_confirm)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val M : TextView =dialog.findViewById(R.id.MSP)
        val P : TextView =dialog.findViewById(R.id.Investment)
        val Confirm : Button = dialog.findViewById(R.id.opt_2)
        val Back : Button = dialog.findViewById(R.id.opt_1)

        M.text=m
        P.text=i

        Log.d("microfinancesActivity", "showConfirm function called")
        Log.d("microfinancesActivity", "m: $m")
        Log.d("microfinancesActivity", "i: $i")

        Back.setOnClickListener {
            dialog.dismiss()
        }

        Confirm.setOnClickListener {
            uploadData()
        }

        dialog.show()
    }
    fun uploadData(){
        database = FirebaseDatabase.getInstance().getReference("MFSellers")
        val MFSeller=MFSeller(selectedCrop,CropWeight.text.toString(),Price.text.toString())
        Log.d("microfinancesActivity", "Saving data to Firebase: $MFSeller")

        val mfRef=database.child(user).push()

        Log.d("microfinancesActivity", "uploadData function called")
        Log.d("microfinancesActivity", "Uploading data: $MFSeller")


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
    fun previous(view: View) {
        startActivity(Intent(this, home2Activity::class.java))
        finish()
    }
    fun RedirectStocks(view:View){

        startActivity(Intent(this, StocksActivity::class.java))
    }
}