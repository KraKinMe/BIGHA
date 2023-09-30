package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter2(private val userList : ArrayList<Machines>): RecyclerView.Adapter<MyAdapter2.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item2,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.cropName.text=currentItem.name
        holder.cropWeight.text = currentItem.desc
        holder.cropPrice.text = currentItem.price
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val cropName : TextView = itemView.findViewById<TextView>(R.id.CropName)
        val cropWeight : TextView = itemView.findViewById<TextView>(R.id.CropWeight)
        val cropPrice : TextView = itemView.findViewById<TextView>(R.id.CropPrice)

    }
}