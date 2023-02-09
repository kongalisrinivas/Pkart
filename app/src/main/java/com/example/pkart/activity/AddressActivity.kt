package com.example.pkart.activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pkart.databinding.ActivityAddressBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AddressActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddressBinding

    private lateinit var preference: SharedPreferences

    private lateinit var totalCost: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preference = this.getSharedPreferences("user", MODE_PRIVATE)

        totalCost = intent.getStringExtra("totalCost")!!

        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loaduserOnfo()

        binding.buttonCheckout.setOnClickListener {
            validateData(
                binding.userName.text.toString(),
                binding.userPhNum.text.toString(),
                binding.userVillage.text.toString(),
                binding.State.text.toString(),
                binding.city.text.toString(),
                binding.userPincode.text.toString()
            )
        }
    }

    private fun validateData(
        name: String,
        number: String,
        village: String,
        state: String,
        city: String,
        pinCode: String
    ) {
        if (name.isEmpty() || number.isEmpty() || village.isEmpty() || state.isEmpty() || city.isEmpty() || pinCode.isEmpty()) {
            Toast.makeText(this, "Enter All Field", Toast.LENGTH_LONG).show()
        } else {
            storeData(name, number, village, state, city, pinCode)
        }
    }

    private fun storeData(
        village: String,
        state: String,
        city: String,
        pinCode: String,
        city1: String,
        pinCode1: String
    ) {
        val map = hashMapOf<String, Any>()
        map["village"] = village
        map["state"] = state
        map["city"] = city
        map["pinCode"] = pinCode
        Firebase.firestore.collection("users").document(preference.getString("number", "")!!)
            .update(map).addOnSuccessListener {

                val b = Bundle()
                b.putStringArrayList("productIds", intent.getStringArrayListExtra("productIds"))
                b.putString("totalCost", totalCost)

                val intent = Intent(this, CheckOutActivity::class.java)
                intent.putExtras(b)
                startActivity(intent)

                Toast.makeText(this, "update successful", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this, "update Failed", Toast.LENGTH_LONG).show()
            }
    }

    private fun loaduserOnfo() {
        Firebase.firestore.collection("users")
            .document(preference.getString("number", "")!!)
            .get().addOnSuccessListener {
                binding.userName.setText(it.getString("userName"))
                binding.userPhNum.setText(it.getString("userNumber"))
                binding.userVillage.setText(it.getString("village"))
                binding.State.setText(it.getString("state"))
                binding.city.setText(it.getString("city"))
                binding.userPincode.setText(it.getString("pinCode"))
                Toast.makeText(this, "user info loaded Successful", Toast.LENGTH_LONG).show()
            }.addOnFailureListener {
                Toast.makeText(this, "load user info Failed", Toast.LENGTH_LONG).show()
            }
    }
}