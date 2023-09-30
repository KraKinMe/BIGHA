package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList

class YourRentals : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private val ToolList = ArrayList<Machines>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_your_rentals)

        val sharedPref=getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val savedUserName=sharedPref.getString("User","def")?:"."

        val database = FirebaseDatabase.getInstance().reference.child("Machines")
        val query = database.child(savedUserName)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val stockList = ArrayList<MFSeller>()
                for (childSnapshot in dataSnapshot.children) {
                    var name = childSnapshot.child("name").getValue(String::class.java) ?: ""
                    var price = childSnapshot.child("price").getValue(String::class.java) ?: ""
                    var image = childSnapshot.child("image").getValue(String::class.java) ?: ""

                    var desc = childSnapshot.child("desc").getValue(String::class.java) ?: ""

                    var location = childSnapshot.child("location").getValue(String::class.java) ?: ""
                    val mf = Machines(name,desc,image,price,location)
                    ToolList.add(mf)
                }
                val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
                val adapter = MyAdapter2(ToolList) // Pass the stockList to the adapter
                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@YourRentals)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error
            }

        }
        )

    }
    fun previous(view : View){
        startActivity(Intent(this,RentActivity::class.java))
    }
}