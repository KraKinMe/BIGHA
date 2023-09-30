package com.example.myapplication

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_home, container, false)
        val sharedPref = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val savedUserName = sharedPref.getString("User", null)?:"."
        val userName = sharedPref.getString("name",".")?:"."
        val userPage = v.findViewById<TextView>(R.id.username)
        userPage.text=userName
        //MicroFinance Button
        val microBtn = v.findViewById<CardView>(R.id.card_microfinances)
        microBtn.setOnClickListener {
            val database = FirebaseDatabase.getInstance().getReference("VerifiedFarmers")
            var allow = false

            database.child(savedUserName).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.d("MyApp", "DataSnapshot exists: ${dataSnapshot.exists()}")

                    if (dataSnapshot.exists()) {
                        allow = true
                    }
                    if (allow) {
                        val intent = Intent(requireActivity(), microfinancesActivity::class.java)
                        requireActivity().startActivity(intent)
                    } else {
                        Log.d("MyApp", "Navigating to home2Activity")
                        allow = false
                        val intent = Intent(requireActivity(), verifylockActivity::class.java)
                        requireActivity().startActivity(intent)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("MyApp", "DatabaseError: ${databaseError.message}")
                    allow = false
                }
            })
        }



        val marketBtn = v.findViewById<CardView>(R.id.card_marketplace)
        marketBtn.setOnClickListener {
            val intent = Intent(requireActivity(), mpsellActivity::class.java)
            requireActivity().startActivity(intent)

        }
        val rentalBtn = v.findViewById<CardView>(R.id.card_rental)
        rentalBtn.setOnClickListener {
            val intent = Intent(requireActivity(), RentActivity::class.java)
            requireActivity().startActivity(intent)

        }
        val toursimBtn = v.findViewById<CardView>(R.id.card_tourism)
        toursimBtn.setOnClickListener {
            val intent = Intent(requireActivity(), agrotourismActivity::class.java)
            requireActivity().startActivity(intent)

        }

        //KNOW MORE BUTTONS

        val KM_MF = v.findViewById<ImageView>(R.id.iconinfo1)
        val KM_MP = v.findViewById<ImageView>(R.id.iconinfo2)
        val KM_Rent = v.findViewById<ImageView>(R.id.iconinfo3)
        val KM_Agro = v.findViewById<ImageView>(R.id.iconinfo4)

        KM_MF.setOnClickListener {
            val FName:String?="Krishi Nivesh"
            val FExplain:String?="Farmers can get Payment for Crops before Sowing them to get Financial Support"
            showCustomDialogBox1(FName,FExplain)
        }
        KM_MP.setOnClickListener {
            val FName:String?="Market Place"
            val FExplain:String?="Farmers can make a Direct Contact with any Buyer"
            showCustomDialogBox1(FName,FExplain)
        }
        KM_Rent.setOnClickListener {
            val FName:String?="Rental Platform"
            val FExplain:String?="Farmers can Rent out as well as Take others equipments on Rent"
            showCustomDialogBox1(FName,FExplain)
        }
        KM_Agro.setOnClickListener {
            val FName:String?="Agro Tourism"
            val FExplain:String?="Farmers can provide tourism in their farm with the help of Agro-Sahayak"
            showCustomDialogBox1(FName,FExplain)
        }

            return v;
    }
//    fun redirectMicro(view: View) {
//        val intent = Intent(requireActivity(), microfinancesActivity::class.java)
//        requireActivity().startActivity(intent)
//        requireActivity().finish()
//    }



    private fun showCustomDialogBox1(FName: String?,FExplain:String?) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_know_more)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val FeatureName : TextView =dialog.findViewById(R.id.FeatureName)
        val FeatureExplain : TextView =dialog.findViewById(R.id.FeatureExplain)
        val Close : ImageView = dialog.findViewById(R.id.closethis)

        FeatureName.text=FName
        FeatureExplain.text=FExplain

        Close.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Home().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}