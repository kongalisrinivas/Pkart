package com.example.pkart.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pkart.R
import com.example.pkart.databinding.ActivityGitBinding

class gitActivity : AppCompatActivity() {
    private lateinit var binding:ActivityGitBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_git)
    }
}