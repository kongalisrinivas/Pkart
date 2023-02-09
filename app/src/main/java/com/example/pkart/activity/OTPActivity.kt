package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pkart.databinding.ActivityOtpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class OTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.butOTP.setOnClickListener {

            if (binding.userOtp.text.toString().isEmpty())
                Toast.makeText(this, "Provide Otp number", Toast.LENGTH_LONG).show()
            else
                verifyUser(binding.userOtp.text.toString())
        }
    }

    private fun verifyUser(otp: String) {
        val credential =
            PhoneAuthProvider.getCredential(intent.getStringExtra("verificationId")!!, otp)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        FirebaseAuth.getInstance().signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    val preference = this.getSharedPreferences("user", MODE_PRIVATE)
                    val editor = preference.edit()
                    editor.putString("number", intent.getStringExtra("number")!!)
                    editor.apply()

                    startActivity(Intent(this@OTPActivity, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this@OTPActivity, "Something went Wrong", Toast.LENGTH_LONG)
                        .show()
                }
                // Update UI
            }
    }
}
