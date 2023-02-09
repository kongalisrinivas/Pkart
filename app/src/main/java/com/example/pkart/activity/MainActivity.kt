package com.example.pkart.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.fragment.findNavController
import com.example.pkart.R
import com.example.pkart.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var i = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (FirebaseAuth.getInstance().currentUser == null) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)
        val navController = navHostFragment!!.findNavController()

        //popupmenu
        val popupMenu = PopupMenu(this, binding.root)
        popupMenu.inflate(R.menu.bottom_nav)
        binding.bottomBar.setupWithNavController(popupMenu.menu, navController)

        //
        navController.addOnDestinationChangedListener { _, destination, _ ->

            title = when (destination.id) {
                R.id.cartFragment -> "My Cart"
                R.id.moreFragment -> "My DashBoard"
                else -> "P-Kart"
            }
        }

        binding.bottomBar.onItemReselected = {
            when (it) {
                0 -> {
                    i = 0
                    navController.navigate(R.id.homeFragment)
                }
                1 -> i = 1
                2 -> i = 2
            }
        }
    }

    override fun onBackPressed() {
        super.getOnBackPressedDispatcher()
        if (i == 0) {
            finish()
        }
    }
}