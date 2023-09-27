package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.content.Context
import android.view.View
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.databinding.ActivityHome2Binding

class home2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityHome2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHome2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(Home())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){

                R.id.home ->replaceFragment(Home())
                R.id.disease_detection ->replaceFragment(DiseaseDetection())
                R.id.profile->replaceFragment(Profile())

                else->{


                }
            }
            true
        }
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Do you really want to close this app?")
            .setPositiveButton("Yes") { _, _ ->
                // Close the app
                finish()
            }
            .setNegativeButton("No") { _, _ ->
                // Do nothing, continue with the app
            }
            .show()
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager =supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}