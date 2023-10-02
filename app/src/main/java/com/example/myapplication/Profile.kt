package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.content.Context
import android.graphics.Color
import android.util.Log
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {
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

    private lateinit var database: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v= inflater.inflate(R.layout.fragment_profile, container, false)
        val backBtn = v.findViewById<ImageView>(R.id.backBtn)
        val sharedPrefUserName = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        val user = sharedPrefUserName.getString("User", "John Doe")?:"."
        val user_name=v.findViewById<TextView>(R.id.user_name)
        // you have to access user name from firebase based on this user

        database=FirebaseDatabase.getInstance().getReference("VerifiedFarmers")

        database.child(user).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                updateVerificationStatus(dataSnapshot.exists())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Database error: ${error.message}")
                // Set the text and color here
                updateVerificationStatus(false)
            }
        })




        user_name.text=sharedPrefUserName.getString("name",".")

        val user_email=v.findViewById<TextView>(R.id.user_email)
        user_email.text=user

        backBtn.setOnClickListener{
            val intent = Intent(requireActivity(), home2Activity::class.java)
            requireActivity().startActivity(intent)
        }
        val signOut = v.findViewById<ImageView>(R.id.signOut)
        signOut.setOnClickListener{
            //Ticking off KMSI
            val sharedPrefs = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            val editor = sharedPrefs.edit()
            editor.remove("User")
            editor.remove("name")
            editor.remove("KMSI")
            editor.apply()


            val intent = Intent(requireActivity(), signUpActivity::class.java)
            requireActivity().startActivity(intent)
            requireActivity().finish()
        }
        return v
    }

    private fun updateVerificationStatus(isVerified: Boolean) {
        val VON = requireView().findViewById<TextView>(R.id.VerifiedOrNot)
        if (isVerified) {
            VON.text = "You are Verified"
            VON.setTextColor(Color.GREEN)
        } else {
            VON.text = "You are Not Verified"
            VON.setTextColor(Color.RED)
        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Profile.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Profile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}