package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

        //MicroFinance Button
        val microBtn = v.findViewById<ImageView>(R.id.arrow_icon1)
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



        val marketBtn = v.findViewById<ImageView>(R.id.arrow_icon2)
        marketBtn.setOnClickListener {
            val intent = Intent(requireActivity(), mpsellActivity::class.java)
            requireActivity().startActivity(intent)

        }
        val rentalBtn = v.findViewById<ImageView>(R.id.arrow_icon3)
        rentalBtn.setOnClickListener {
            val intent = Intent(requireActivity(), RentActivity::class.java)
            requireActivity().startActivity(intent)

        }
        val toursimBtn = v.findViewById<ImageView>(R.id.arrow_icon4)
        toursimBtn.setOnClickListener {
            val intent = Intent(requireActivity(), agrotourismActivity::class.java)
            requireActivity().startActivity(intent)

        }
            return v;
    }
//    fun redirectMicro(view: View) {
//        val intent = Intent(requireActivity(), microfinancesActivity::class.java)
//        requireActivity().startActivity(intent)
//        requireActivity().finish()
//    }

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