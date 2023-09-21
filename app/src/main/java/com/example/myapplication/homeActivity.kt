package com.example.myapplication

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import java.lang.ProcessBuilder.Redirect

class homeActivity : AppCompatActivity() {
    lateinit var toggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        window.statusBarColor = Color.rgb(0,0,0)
        val drawerLayout:DrawerLayout = findViewById(R.id.drawer)
        val navView:NavigationView = findViewById(R.id.nav_view)

        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val btnKnowMoreMicroFin : Button = findViewById(R.id.btnKnowMoreMicroFin)

        btnKnowMoreMicroFin.setOnClickListener {
            val FName:String?="MICRO FINANCE"
            val FExplain:String?="Here Farmers can do Stock level shit (Baadme Explain karna)"
            showCustomDialogBox(FName,FExplain)
        }

        val btnKnowMoreRent : Button = findViewById(R.id.btnKnowMoreRent)

        btnKnowMoreRent.setOnClickListener {
            val FName:String?="Rental Platform"
            val FExplain:String?="Farmers can Rent out as well as Take others items on Rent"
            showCustomDialogBox(FName,FExplain)
        }
    }

    private fun showCustomDialogBox(FName: String?,FExplain:String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_know_more)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val FeatureName : TextView =dialog.findViewById(R.id.FeatureName)
        val FeatureExplain : TextView =dialog.findViewById(R.id.FeatureExplain)
        val btnBackKM : Button = dialog.findViewById(R.id.btnBackKM)

        FeatureName.text=FName
        FeatureExplain.text=FExplain

        btnBackKM.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)) return true
        return super.onOptionsItemSelected(item)
    }
    fun RedirectRent(view: View){
        startActivity(Intent(this,RentActivity::class.java))

    }
    fun RedirectAgro(view: View){
        startActivity(Intent(this,agrotourismActivity::class.java))
        finish()

    }
}