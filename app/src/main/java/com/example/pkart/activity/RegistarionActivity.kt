package com.example.pkart.activity

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pkart.databinding.ActivityRegistarionBinding
import com.example.pkart.model.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistarionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistarionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistarionBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.butLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        binding.butSignup.setOnClickListener {

            validateUser()
        }
    }

    private fun validateUser() {
        if (binding.usernum.text.toString().isEmpty() || binding.name.text.toString().isEmpty())
            Toast.makeText(this, "Enter Fields", Toast.LENGTH_SHORT).show()
        else
            storeData()
    }

    private fun storeData() {
        val builder = AlertDialog.Builder(this).setTitle("Loading...")
            .setMessage("Please wait..")
            .setCancelable(true)
            .create()
        builder.show()

        val preferenc = this.getSharedPreferences("user", MODE_PRIVATE)
        val editor = preferenc.edit()
        editor.putString("number", binding.usernum.text.toString())
        editor.putString("name", binding.name.text.toString())
        editor.apply()

        val data = UserModel(
            userName = binding.name.text.toString(),
            userPhoneNumber = binding.usernum.text.toString()
        )

        Firebase.firestore.collection("users").document(binding.usernum.text.toString()).set(data)
            .addOnSuccessListener {
                Toast.makeText(this, "User Register", Toast.LENGTH_SHORT).show()
                builder.dismiss()
                openLogin()
            }.addOnFailureListener {
                builder.dismiss()
                Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
            }
    }

    private fun openLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}