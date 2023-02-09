package com.example.pkart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pkart.databinding.AllOrderItemLayoutBinding
import com.example.pkart.model.AllOrderModel

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllOrderAdapter(val context: Context, val list: ArrayList<AllOrderModel>) :
    RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>() {

    inner class AllOrderViewHolder(var binding: AllOrderItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        val binding =
            AllOrderItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AllOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.productName.text = list[position].name
        holder.binding.productPrice.text = list[position].price

        when (list[position].status) {
            "Ordered" -> {
                holder.binding.status.text = "ordered"
            }
            "Dispatched" -> {
                holder.binding.status.text = "Dispatched"
            }
            "Delivered" -> {

                holder.binding.status.text = "Delivered"
            }
            "Cancelled" -> {
                holder.binding.status.text = "Cancelled"
            }
        }
    }

    private fun updateStatus(str: String, doc: String) {
        val data = hashMapOf<String, Any>()
        data["status"] = str
        Firebase.firestore.collection("allOrders").document(doc).update(data)
            .addOnSuccessListener {
                Toast.makeText(context, "status Updates", Toast.LENGTH_LONG).show()
            }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}