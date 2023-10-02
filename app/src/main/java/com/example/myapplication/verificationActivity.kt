package com.example.myapplication



import android.Manifest
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.net.Uri
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.myapplication.databinding.ActivityVerificationBinding
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsStatusCodes
import com.google.android.gms.location.Priority
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.io.IOException
import java.util.Locale


class verificationActivity : AppCompatActivity() {
    private var userAddress: String? = null
    private lateinit var FarmArea:EditText
    private lateinit var LandID:EditText
    private lateinit var Verify:Button
    private lateinit var database: DatabaseReference
    private lateinit var binding: ActivityVerificationBinding
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var man_Pin :String?=null
    private var man_JanPad :String?=null
    private var man_Tehsil :String?=null
    private var man_Village :String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityVerificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

        Verify = findViewById(R.id.VerifyMe)

        FarmArea = findViewById(R.id.FarmArea)
        LandID = findViewById(R.id.LandNo)

        binding.getLocation.setOnClickListener(){
            //Permission Check
            checkLocationPermission()
        }

        binding.getManual.setOnClickListener {
            showCustomDialogBox1()

            userAddress="${man_Village},${man_Tehsil},${man_JanPad},${man_Pin},"
        }

        Verify.setOnClickListener {
            Toast.makeText(this,"Sending ...",Toast.LENGTH_SHORT).show()

            val FarmArea=FarmArea.text.toString()
            val LandID=LandID.text.toString()
            val sharedPref=getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val savedUserName=sharedPref.getString("User","ghfhgf")?:"."

            database = FirebaseDatabase.getInstance().getReference("VerifiedFarmers")
            val VerifiedFarmers=VerifiedFarmers(userAddress,LandID,FarmArea)

            database.child(savedUserName).setValue(VerifiedFarmers).addOnSuccessListener {
                Toast.makeText(this,"You Are Verified",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,home2Activity::class.java))
                finish()
            }.addOnCanceledListener {
                Toast.makeText(this,"Kuch to Gadbad Hai Daya",Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun showCustomDialogBox1() {
        val dialog = Dialog(this) // Use 'this' to refer to the activity's context
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_add_address)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val Close : ImageView = dialog.findViewById(R.id.closethis)
        val pinEditText: EditText = dialog.findViewById(R.id.man_pin)
        val janPadEditText: EditText = dialog.findViewById(R.id.man_janpad)
        val tehsilEditText: EditText = dialog.findViewById(R.id.man_tehsil)
        val villageEditText: EditText = dialog.findViewById(R.id.man_village)
        val btnSave:Button=dialog.findViewById(R.id.Save)

        Close.setOnClickListener {
            dialog.dismiss()
        }

        btnSave.setOnClickListener {
            man_Pin = pinEditText.text.toString()
            man_JanPad = janPadEditText.text.toString()
            man_Tehsil = tehsilEditText.text.toString()
            man_Village = villageEditText.text.toString()

            dialog.dismiss()
        }

        dialog.show()
    }

    private fun checkLocationPermission() {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
            //Permission is ther
            checkGPS() //This to turn Location
        }
        else{
            //No Permission
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),100)

        }
    }

    private fun checkGPS() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(2000)
            .setMaxUpdateDelayMillis(5000)
            .build()

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
            .setAlwaysShow(true)

        val settingsClient = LocationServices.getSettingsClient(this.applicationContext)
        val locationSettingsResponseTask = settingsClient.checkLocationSettings(builder.build())

        locationSettingsResponseTask.addOnCompleteListener { task ->
            try {
                // GPS is ON
                val response = task.getResult(ApiException::class.java)
                getUserLocation()
            } catch (exception: ApiException) {
                // GPS is OFF
                when (exception.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        try {
                            // Request to turn on GPS
                            val resolvable = exception as ResolvableApiException
                            resolvable.startResolutionForResult(this, 200)
                        } catch (sendEx: IntentSender.SendIntentException) {
                            // Handle the error.
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        // Location settings are inadequate, and cannot be fixed here. Fix in Settings.
                    }
                }
            }
        }
    }

    private fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),100)
            return
        }
        fusedLocationProviderClient.lastLocation.addOnCompleteListener{task ->
            val location=task.result

            if(location != null){
                try{
                    val geocoder=Geocoder(this, Locale.getDefault())

                    val addressList=geocoder.getFromLocation(location.latitude,location.longitude,1)

                    //Finally Address mil gaya

                    if (addressList != null && addressList.isNotEmpty()) {
                        userAddress = addressList[0].getAddressLine(0) // Store the address in the variable
                        binding.Loacation.setText(userAddress) // Update the UI with the address
                    }

                    openLocation(userAddress.toString())

                }catch(e: IOException){

                }
            }

        }
    }

    private fun openLocation(loction: String) {
        //Opening Location in google Maps

        binding.Loacation.setOnClickListener {
            if(binding.Loacation.text.isEmpty()){
                //When the location is not Empty
                val uri= Uri.parse("geo:0,0?q=$loction")
                val intent=Intent(Intent.ACTION_VIEW,uri)
                intent.setPackage("com.google.android.apps.maps")
                startActivity(intent)
            }
        }



    }

    fun previous(view : View){
        startActivity(Intent(this,home2Activity::class.java))
        finish()
    }

}