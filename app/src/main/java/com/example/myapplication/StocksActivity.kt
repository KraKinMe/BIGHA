package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import com.google.firebase.database.*

import java.util.ArrayList


class StocksActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private val stockList = ArrayList<MFSeller>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stocks)
        val sharedPref=getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val savedUserName=sharedPref.getString("User","def")?:"."

        val database = FirebaseDatabase.getInstance().reference.child("MFSellers")
        val query = database.child(savedUserName)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val stockList = ArrayList<MFSeller>()
                for (childSnapshot in dataSnapshot.children) {
                    var name = childSnapshot.child("crop").getValue(String::class.java) ?: ""
                    var price = childSnapshot.child("price").getValue(String::class.java) ?: ""
                    var weight = childSnapshot.child("weight").getValue(String::class.java) ?: ""
                    val mf = MFSeller(name,weight,price)
                    stockList.add(mf)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                val adapter = MyAdapter(stockList) // Pass the stockList to the adapter
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@StocksActivity)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error
            }

        }
            )

}
}