package com.geekym.navigation_component_test

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.geekym.navigation_component_test.databinding.FragmentHomepageBinding
import com.geekym.navigation_component_test.databinding.FragmentUserListBinding
import com.google.firebase.database.*


class UserList_Fragment : Fragment() {

    private var _binding : FragmentUserListBinding?= null
    private val binding get() = _binding!!
    private lateinit var db : DatabaseReference
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userArrayList : ArrayList<Users>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserListBinding.inflate(layoutInflater, container, false)

        userRecyclerView = binding.recView
        userRecyclerView.layoutManager = LinearLayoutManager(context)
        userRecyclerView.setHasFixedSize(true)
        userArrayList = arrayListOf<Users>()
        getUserData()

        return binding.root
    }

    private fun getUserData() {
        db = FirebaseDatabase.getInstance().reference.child("Users")
     //   db = FirebaseDatabase.getInstance().getReference("Users")
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(Users::class.java)
                        userArrayList.add(user!!)
                    }
                    userRecyclerView.adapter = myAdapter(userArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}