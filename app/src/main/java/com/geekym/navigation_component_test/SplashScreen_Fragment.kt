package com.geekym.navigation_component_test

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.geekym.navigation_component_test.databinding.FragmentSplashScreenBinding

class SplashScreen_Fragment : Fragment() {
    private var _binding : FragmentSplashScreenBinding?=null
    private val binding get() = _binding!!
    lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)

        handler = Handler()
        handler.postDelayed({

            // Delay and Start Activity
            Navigation.findNavController(binding.root).navigate(R.id.action_splashScreen_Fragment_to_signin_Fragment)
        } , 2700) // here we're delaying to startActivity after 3seconds

        return binding.root
    }
}