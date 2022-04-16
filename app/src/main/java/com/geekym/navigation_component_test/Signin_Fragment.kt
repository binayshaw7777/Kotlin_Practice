package com.geekym.navigation_component_test

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.geekym.navigation_component_test.databinding.FragmentSigninBinding
import com.google.firebase.auth.FirebaseAuth

class Signin_Fragment : Fragment() {

    private var _binding : FragmentSigninBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSigninBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()

        binding.textView3.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_signin_Fragment_to_signup_Fragment)
        }

        binding.forgot.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_signin_Fragment_to_forgotPassword_Fragment)
        }

        binding.button.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            val pass = binding.editTextTextPassword.text.toString().trim()

            if(email.isNotEmpty() && pass.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if(it.isSuccessful && auth.currentUser?.isEmailVerified!!) {
                        Toast.makeText(activity, "Loggin In Successfully", Toast.LENGTH_LONG).show()
                        Navigation.findNavController(binding.root).navigate(R.id.action_signin_Fragment_to_homepage)
                    } else {
                        val u = auth.currentUser
                        u?.sendEmailVerification()
                        Toast.makeText(activity, "Email Verification sent to your mail", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(activity, "Please enter details", Toast.LENGTH_LONG).show()
            }
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val user = auth.currentUser
        if (user != null && user.isEmailVerified) {
            // User is signed in
            Navigation.findNavController(binding.root).navigate(R.id.action_signin_Fragment_to_homepage)
        } else {
            // No user is signed in
            Toast.makeText(activity, "Please enter details", Toast.LENGTH_LONG).show()
        }
    }
}