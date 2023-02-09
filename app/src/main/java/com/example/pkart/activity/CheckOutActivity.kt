package com.example.pkart.activity

import android.content.ContentValues.TAG
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.pkart.databinding.ActivityCheckOutBinding
import com.example.pkart.roomdb.AppDatabase
import com.example.pkart.roomdb.ProductModul

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class CheckOutActivity : AppCompatActivity(), PaymentResultListener {
    private lateinit var binding: ActivityCheckOutBinding
    private lateinit var preference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckOutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val price = this.intent.getStringExtra("totalCost")
        val checkout = Checkout()
        checkout.setKeyID("YOUR_KEY_ID");
        try {
            val options = JSONObject();

            options.put("name", "PkART");
            options.put("description", "Best E Commerce App");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.jpg");
            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#4A1D9C");
            options.put("currency", "INR");
            options.put("amount", (price!!.toInt() * 100));//pass amount in currency subunits
            options.put("prefill.email", "srinivas.kongali@gmail.com");
            options.put("prefill.contact", "9966881627");

            checkout.open(this, options);

        } catch (e: Exception) {
            Toast.makeText(this, "Error in starting Razorpay Checkout", Toast.LENGTH_LONG).show()
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    override fun onPaymentSuccess(p0: String?) {

        uploadData()

        Toast.makeText(this, "Payment Success", Toast.LENGTH_LONG).show()
    }

    private fun uploadData() {
        val id = intent.getStringArrayListExtra("productIds")
        for (currentId in id!!) {
            fetchData(currentId)
        }
    }

    private fun fetchData(productId: String) {

        val dao = AppDatabase.getInstance(this).productDao()

        Firebase.firestore.collection("products").document(productId)
            .get()
            .addOnSuccessListener {

                lifecycleScope.launch(Dispatchers.IO) {
                    dao.deleteProduct(
                        ProductModul(
                            productId,
                            productImage = "",
                            productName = "",
                            productSP = ""
                        )
                    )
                }

                saveData(it.getString("productName"), it.getString("productSP"), productId)
            }
            .addOnFailureListener {

                Toast.makeText(this, "Product data get Success", Toast.LENGTH_LONG).show()

            }
    }

    private fun saveData(name: String?, price: String?, productId: String) {

        preference = this.getSharedPreferences("user", MODE_PRIVATE)

        val data = hashMapOf<String, Any>()
        data["name"] = name!!
        data["price"] = price!!
        data["productId"] = productId
        data["status"] = "Ordered"
        data["userId"] = preference.getString("number", "")!!

        val fireStore = Firebase.firestore.collection("allOrders")
        val key = fireStore.document().id

        data["orderId"] = key

        fireStore.document(key).set(data).addOnSuccessListener {

            Toast.makeText(this, "order Placed ", Toast.LENGTH_LONG).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "unSuccessful", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_LONG).show()
    }
}