package com.geekym.navigation_component_test

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.geekym.navigation_component_test.databinding.FragmentHomepageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.File

class Homepage : Fragment() {

    private var _binding : FragmentHomepageBinding ?= null
    private val binding get() = _binding!!
    private lateinit var db : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomepageBinding.inflate(layoutInflater, container, false)

        db = FirebaseDatabase.getInstance().reference!!.child("Users")
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        Toast.makeText(activity, user.toString(), Toast.LENGTH_LONG).show()
        val userreference = db.child(user?.uid!!)

        userPP()

        userreference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.textView4.text = snapshot.child("email").value.toString() + "\n" + snapshot.child("name").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        binding.delete.setOnClickListener {
            val uid = auth.currentUser?.uid
            db.child(uid!!).setValue(null)
            auth.currentUser!!.delete().addOnSuccessListener {
                Toast.makeText(activity, "User Deleted Successfully", Toast.LENGTH_LONG).show()
                Navigation.findNavController(binding.root).navigate(R.id.action_homepage_to_signin_Fragment)
            }
        }

        binding.update.setOnClickListener {
            val name = binding.editTextTextPersonName2.text.toString()
            val uid = auth.currentUser?.uid
            db.child(uid!!).child("name").setValue(name).addOnCompleteListener {
                if(it.isSuccessful) {
                    Toast.makeText(activity, "User updated successfully", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(activity, "User data failed to update", Toast.LENGTH_LONG).show()
                }
            }
        }

        binding.logout.setOnClickListener {
            auth.signOut()
            Toast.makeText(activity, "Logged out Successfully", Toast.LENGTH_LONG).show()
            Navigation.findNavController(binding.root).navigate(R.id.action_homepage_to_signin_Fragment)

        }

        return binding.root
    }

    private fun userPP() {
        val uid = Firebase.auth.currentUser?.uid.toString()
        var storageReference : StorageReference = FirebaseStorage.getInstance().reference.child("Users/$uid")
        val localFile = File.createTempFile("tempImage","jpg")
        storageReference.getFile(localFile).addOnSuccessListener {
            val bitmap = BitmapFactory.decodeFile(localFile.absolutePath)
            binding.image.setImageBitmap(bitmap)
        }
    }
}