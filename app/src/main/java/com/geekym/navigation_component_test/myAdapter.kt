package com.geekym.navigation_component_test

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class myAdapter (private val userlist : ArrayList<Users>): RecyclerView.Adapter<myAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_view, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userlist[position]
        holder.nm.text = currentItem.Name
        holder.em.text = currentItem.Email
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    class MyViewHolder (itemView : View): RecyclerView.ViewHolder(itemView) {
        val nm : TextView = itemView.findViewById(R.id.heading)
        val em : TextView = itemView.findViewById(R.id.desc)
    }
}