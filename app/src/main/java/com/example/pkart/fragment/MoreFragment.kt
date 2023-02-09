package com.example.pkart.fragment

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pkart.R
import com.example.pkart.adapter.AllOrderAdapter
import com.example.pkart.databinding.FragmentMoreBinding
import com.example.pkart.model.AllOrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase


class MoreFragment : Fragment() {

    private lateinit var binding: FragmentMoreBinding
    private lateinit var list: ArrayList<AllOrderModel>
    private lateinit var preference: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoreBinding.inflate(layoutInflater)

        preference = requireContext().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)

        list = ArrayList()
        Firebase.firestore.collection("allOrders")
            .whereEqualTo("userId", preference.getString("number", "")!!)
            .get().addOnSuccessListener {

                for (doc in it) {
                    val data = doc.toObject(AllOrderModel::class.java)
                    list.add(data)
                }
                binding.rvstatus.adapter = AllOrderAdapter(requireContext(), list)
            }.addOnFailureListener {
                Toast.makeText(requireContext(), "SomeThing Went Wrong", Toast.LENGTH_LONG).show()
            }
        return binding.root
    }
}