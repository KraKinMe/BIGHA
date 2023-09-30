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
import java.util.Locale
import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class homeActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val change=intent.getStringExtra("key_name")

        if(change!=null){
            val cur=getCurrentLocaleLanguage(this)
            if (cur == "en") {
                val hindiLocale = Locale("hi")
                Locale.setDefault(hindiLocale)

                val config = Configuration()
                config.locale = hindiLocale
                resources.updateConfiguration(config, resources.displayMetrics)
            } else {
                val englishLocale = Locale("en")
                Locale.setDefault(englishLocale)

                val config = Configuration()
                config.locale = englishLocale
                resources.updateConfiguration(config, resources.displayMetrics)
            }

        }

        setContentView(R.layout.activity_home)
        window.statusBarColor = Color.rgb(0,0,0)


        //KNOW MORE BUTTONS
        val btnKnowMoreMicroFin : Button = findViewById(R.id.btnKnowMoreMicroFin)

        btnKnowMoreMicroFin.setOnClickListener {
            val FName:String?="MICRO FINANCE"
            val FExplain:String?="Here Farmers can do Stock level shit (Baadme Explain karna)"
            showCustomDialogBox1(FName,FExplain)
        }

        val btnKnowMoreRent : Button = findViewById(R.id.btnKnowMoreRent)

        btnKnowMoreRent.setOnClickListener {
            val FName:String?="Rental Platform"
            val FExplain:String?="Farmers can Rent out as well as Take others items on Rent"
            showCustomDialogBox1(FName,FExplain)
        }


        //QUESTION ASKING BUTTONS

        val btnGoTOKN : Button = findViewById(R.id.btnGoToKN)

        btnGoTOKN.setOnClickListener {
            val Question:String?="DO YOU WANT INVESTMENT OR WANT TO INVEST?"

            showCustomDialogBox2(Question)
        }

        val btnGoToMP : Button = findViewById(R.id.btnGoToMP)

        btnGoToMP.setOnClickListener {
            val Question:String?="Who Are You ?"

            showCustomDialogBox3(Question)
        }
        drawerLayout = findViewById(R.id.drawer)
        navView= findViewById(R.id.nav_view)
        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            Log.d("Ghusa","ha")
            when(it.itemId){
                R.id.Setting -> {
                    Log.d("MenuItemClick", "Setting menu item clicked")
                    Toast.makeText(this, "Clickkk", Toast.LENGTH_SHORT).show()
                }
                R.id.ChangeLang ->{
                    RedirectHome(navView)
                }
                R.id.LOGOUT ->{
                    Redirectsignuppage(navView)
                }
            }
            true
        }

    }
    fun getCurrentLocaleLanguage(context: Context): String {
        val resources = context.resources
        val configuration: Configuration = resources.configuration
        val locale: Locale = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            configuration.locales[0]
        } else {
            configuration.locale
        }
        return locale.language // Returns the current language code (e.g., "en" for English)
    }
    private fun showCustomDialogBox1(FName: String?,FExplain:String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_know_more)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val FeatureName : TextView =dialog.findViewById(R.id.FeatureName)
        val FeatureExplain : TextView =dialog.findViewById(R.id.FeatureExplain)
//        val btnBackKM : Button = dialog.findViewById(R.id.btnBackKM)

        FeatureName.text=FName
        FeatureExplain.text=FExplain

//        btnBackKM.setOnClickListener {
//            dialog.dismiss()
//        }

        dialog.show()

    }

    private fun showCustomDialogBox2(Ques: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.activity_layout_ques)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val Question : TextView =dialog.findViewById(R.id.question)
        val opt_1 : Button = dialog.findViewById(R.id.opt_1)
        val opt_2 : Button = dialog.findViewById(R.id.opt_2)

        Question.text=Ques

        opt_1.setOnClickListener {
            val sharedPref = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val savedUserName = sharedPref.getString("User", "def") ?: "."
            val database = FirebaseDatabase.getInstance().getReference("VerifiedFarmers")
            var allow = false

// Use a ValueEventListener to check if savedUserName exists in VerifiedFarmers
            database.child(savedUserName).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        allow = true
                    }

                    // Now that we have determined whether the user exists in VerifiedFarmers,
                    // you can start the appropriate activity here.
                    if (allow) {
                        startActivity(Intent(this@homeActivity, microfinancesActivity::class.java))
                    } else {
                        startActivity(Intent(this@homeActivity, verifylockActivity::class.java))
                    }
                    dialog.dismiss()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Handle any errors or cancellations here if needed.
                    // You can set allow to false in case of errors.
                    allow = false
                    dialog.dismiss()
                }
            })
        }
        opt_2.setOnClickListener {

            dialog.dismiss()
        }

        dialog.show()

    }
    private fun showCustomDialogBox3(Ques: String?) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.activity_layout_ques)

        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val Question : TextView =dialog.findViewById(R.id.question)
        val opt_1 : Button = dialog.findViewById(R.id.opt_1)
        val opt_2 : Button = dialog.findViewById(R.id.opt_2)

        Question.text=Ques

        opt_1.setOnClickListener {

            startActivity(Intent(this,mpsellActivity::class.java))
            dialog.dismiss()
        }
        opt_2.setOnClickListener {

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
    }

    fun RedirectHome(view: View){
        val intent = Intent(this, homeActivity::class.java)
        intent.putExtra("key_name", "Hello from MainActivity!")
        startActivity(intent)
    }
    fun RedirectDisease(view: View){
        val intent = Intent(this, detectionActivity::class.java)
        intent.putExtra("key_name", "Hello from MainActivity!")
        startActivity(intent)
    }
    fun RedirectOutbreaks(view: View){
        val intent = Intent(this, OutBreaksActivity::class.java)
        intent.putExtra("key_name", "Hello from MainActivity!")
        startActivity(intent)
    }
    fun redirectMicro(view: View){
        val intent = Intent(this, microfinancesActivity::class.java)
        intent.putExtra("key_name", "Hello from MainActivity!")
        startActivity(intent)
    }

    fun Redirectsignuppage(view: View){
        val intent = Intent(this, signUpActivity::class.java)
        intent.putExtra("key_name", "Hello from MainActivity!")
        startActivity(intent)
    }
    fun btnGoToKN(view:View){
        val intent = Intent(this, mpsellActivity::class.java)
        intent.putExtra("key_name", "Hello from MainActivity!")
        startActivity(intent)

    }

}