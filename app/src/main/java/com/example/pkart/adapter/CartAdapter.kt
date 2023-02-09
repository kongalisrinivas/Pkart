package com.example.pkart.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pkart.activity.ProductDetailsActivity
import com.example.pkart.databinding.LayoutCartItemBinding
import com.example.pkart.roomdb.AppDatabase
import com.example.pkart.roomdb.ProductModul
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartAdapter(var context: Context, var list: ArrayList<ProductModul>) :
    RecyclerView.Adapter<CartAdapter.CartViewModel>() {


    inner class CartViewModel(var binding: LayoutCartItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewModel {
        var binding = LayoutCartItemBinding.inflate(LayoutInflater.from(context), parent, false)

        return CartViewModel(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CartViewModel, position: Int) {
        Glide.with(context).load(list[position].productImage).into(holder.binding.imageView)
        holder.binding.textView3.text = list[position].productName
        holder.binding.textView6.text = list[position].productSP


        holder.itemView.setOnClickListener {
            val intent = Intent(context, ProductDetailsActivity::class.java)
            intent.putExtra("id", list[position].ProductId)
            context.startActivity(intent)
        }
        val dao = AppDatabase.getInstance(context).productDao()
        holder.binding.imageView4!!.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                dao.deleteProduct(
                    ProductModul(
                        list[position].ProductId,
                        list[position].productName,
                        list[position].productImage,
                        list[position].productSP
                    )
                )
            }

        }
    }
}