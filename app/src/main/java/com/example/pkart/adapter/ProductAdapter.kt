package com.example.pkart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pkart.activity.ProductDetailsActivity
import com.example.pkart.databinding.LayoutItemProductBinding
import com.example.pkart.model.AddProductModel


class ProductAdapter(val context: Context, var list: ArrayList<AddProductModel>) :
    RecyclerView.Adapter<ProductAdapter.AddProductImageViewHolder>() {

    inner class AddProductImageViewHolder(val binding: LayoutItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddProductImageViewHolder {
        val binding =
            LayoutItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddProductImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddProductImageViewHolder, position: Int) {
        val data = list[position]
        Glide.with(context).load(data.productCoverImg).into(holder.binding.imageView2)
        holder.binding.textView.text = data.productName
        holder.binding.textView4.text = data.productCategory
        holder.binding.textView5.text = data.productMRP
        holder.binding.button.text = data.productSP
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id", list[position].productId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}