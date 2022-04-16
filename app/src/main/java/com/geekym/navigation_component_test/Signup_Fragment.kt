package com.geekym.navigation_component_test

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.geekym.navigation_component_test.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Signup_Fragment : Fragment() {

    private var _binding : FragmentSignupBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    private lateinit var db : DatabaseReference
    private lateinit var fd : FirebaseDatabase
    private lateinit var sr : StorageReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()
        fd = FirebaseDatabase.getInstance()
        db = fd.getReference("Users")

        _binding = FragmentSignupBinding.inflate(layoutInflater, container, false)

        binding.textView3.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_signup_Fragment_to_signin_Fragment)
        }

        binding.button.setOnClickListener{
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val pass = binding.editTextTextPassword.text.toString().trim()
            val name = binding.editTextTextPersonName.text.toString().trim()

            Toast.makeText(activity, email, Toast.LENGTH_SHORT).show()
            Toast.makeText(activity, pass, Toast.LENGTH_SHORT).show()
            Toast.makeText(activity, name, Toast.LENGTH_SHORT).show()

            var user = Users(name,email)

            if(email.isNotEmpty() && pass.isNotEmpty() && name.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if(it.isSuccessful) {
                        var uid = auth.currentUser?.uid
                        db.child(uid!!).setValue(user).addOnCompleteListener {
                            if(it.isSuccessful) {
                                Navigation.findNavController(binding.root).navigate(R.id.action_signup_Fragment_to_signin_Fragment)
                                uploadProfilePic()
                            }
                        }
                    } else {
                        Toast.makeText(activity, "Failed"+it.exception, Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(activity, "Please enter details", Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    private fun uploadProfilePic() {
        val imageUri = Uri.parse("android.resource://${activity?.packageName}/${R.drawable.pp}")
        sr = FirebaseStorage.getInstance().getReference("Users/"+auth.currentUser?.uid)
        sr.putFile(imageUri).addOnSuccessListener {
            Toast.makeText(activity, "Image Uploaded Successfully", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(activity, "Failed to Upload Image", Toast.LENGTH_LONG).show()
        }
    }

}