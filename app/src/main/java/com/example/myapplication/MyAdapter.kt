package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
class MyAdapter(private val userList : ArrayList<MFSeller>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.cropName.text=currentItem.Crop
        holder.cropWeight.text = currentItem.Weight
        holder.cropPrice.text = currentItem.Price
    }
    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val cropName : TextView = itemView.findViewById<TextView>(R.id.CropName)
        val cropWeight : TextView = itemView.findViewById<TextView>(R.id.CropWeight)
        val cropPrice : TextView = itemView.findViewById<TextView>(R.id.CropPrice)

    }
}