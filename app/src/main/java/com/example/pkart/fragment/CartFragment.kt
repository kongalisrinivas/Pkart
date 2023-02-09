package com.example.pkart.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.pkart.R
import com.example.pkart.activity.AddressActivity
import com.example.pkart.activity.CategoryActivity
import com.example.pkart.adapter.CartAdapter
import com.example.pkart.databinding.FragmentCartBinding
import com.example.pkart.databinding.FragmentHomeBinding
import com.example.pkart.roomdb.AppDatabase
import com.example.pkart.roomdb.ProductModul

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding
    private lateinit var list: ArrayList<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCartBinding.inflate(layoutInflater)


        val preference =
            requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        val editor = preference.edit()
        editor.putBoolean("isCart", false)
        editor.apply()

        val dao = AppDatabase.getInstance(requireContext()).productDao()

        list = ArrayList()
        dao.getAllProducts().observe(requireActivity()) {
            binding.rvcart.adapter = CartAdapter(requireContext(), it as ArrayList<ProductModul>)

            list.clear()
            for (data in it) {
                list.add(data.ProductId)
            }
            totalCost(it)
        }

        return binding.root
    }

    private fun totalCost(data: ArrayList<ProductModul>) {
        var total = 0
        for (item in data) {
            total += item.productSP.toInt()
        }

        binding.totalitem.text = "Total Items: ${data.size}"
        binding.totalitem.text = "Total Cost: $total"

        binding.checkout.setOnClickListener {
            val intent = Intent(requireContext(), AddressActivity::class.java)
            val b = Bundle()
            b.putString("totalCost", total.toString())
            b.putStringArrayList("productIds", list)
intent.putExtras(b)
            startActivity(intent)
        }
    }
}