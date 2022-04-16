package com.geekym.navigation_component_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.geekym.navigation_component_test.databinding.FragmentForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword_Fragment : Fragment() {

    private var _binding : FragmentForgotPasswordBinding?=null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()

        _binding = FragmentForgotPasswordBinding.inflate(layoutInflater,container,false)

        binding.send.setOnClickListener {
            val email = binding.editTextTextEmailAddress.text.toString().trim()
            Toast.makeText(activity, email, Toast.LENGTH_SHORT).show()
            auth.sendPasswordResetEmail(email).addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(activity, "Email sent", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(binding.root)
                        .navigate(R.id.action_forgotPassword_Fragment_to_signin_Fragment)
                } else
                    Toast.makeText(activity, "Email failed to", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}