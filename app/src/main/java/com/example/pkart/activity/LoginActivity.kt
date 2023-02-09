package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pkart.databinding.ActivityLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.butSignup.setOnClickListener {
            startActivity(Intent(this, RegistarionActivity::class.java))
            finish()
        }
        binding.butSignin.setOnClickListener {
            if (binding.userNum.text.toString().isEmpty())
                Toast.makeText(this, "Enter Fields", Toast.LENGTH_SHORT).show()
            else
                sendOtp(binding.userNum.text.toString())

        }
    }

    private lateinit var builder: AlertDialog

    private fun sendOtp(number: String) {

        val builder = android.app.AlertDialog.Builder(this).setTitle("Loading...")
            .setMessage("Please wait..")
            .setCancelable(true)
            .create()
        builder.show()

        val options = PhoneAuthOptions.Builder(FirebaseAuth.getInstance())
            .setPhoneNumber("+91$number")
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(p0: PhoneAuthCredential) {
            TODO("Not yet implemented")
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            TODO("Not yet implemented")
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            builder.dismiss()

            val intent = Intent(this@LoginActivity, OTPActivity::class.java)

            intent.putExtra("verificationId", verificationId)
            intent.putExtra("number", binding.userNum.text.toString())
            startActivity(intent)
        }
    }
}