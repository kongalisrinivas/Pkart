package com.example.pkart.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pkart.R
import com.example.pkart.adapter.ProductAdapter
import com.example.pkart.adapter.CategoryAdapter
import com.example.pkart.databinding.FragmentHomeBinding
import com.example.pkart.model.AddProductModel
import com.example.pkart.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHomeBinding.inflate(layoutInflater)

        getCategory()
        getProduct()
        getSlider()
//sharedprefrence from productDetailActivity
        val preference =
            requireContext().getSharedPreferences("info", AppCompatActivity.MODE_PRIVATE)
        if (preference.getBoolean("isCart", false))
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        return binding.root
    }

    private fun getSlider() {
        Firebase.firestore.collection("slider").document("item").get().addOnSuccessListener {

            Glide.with(requireContext()).load(it.get("img")).into(binding.sliderImage)
        }
    }

    private fun getProduct() {

        val list = ArrayList<AddProductModel>()
        Firebase.firestore.collection("products").get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(AddProductModel::class.java)
                    list.add(data!!)
                }
                binding.productRecycler.adapter = ProductAdapter(requireContext(), list)
            }
    }

    private fun getCategory() {

        val list = ArrayList<CategoryModel>()
        Firebase.firestore.collection("categories").get()
            .addOnSuccessListener {
                list.clear()
                for (doc in it.documents) {
                    val data = doc.toObject(CategoryModel::class.java)
                    list.add(data!!)
                }
                binding.categoryRecycler.adapter = CategoryAdapter(requireContext(), list)
            }
    }
}