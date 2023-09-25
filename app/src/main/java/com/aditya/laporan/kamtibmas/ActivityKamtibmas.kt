package com.aditya.laporan.kamtibmas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.MainActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.ActivityKamtibmasBinding

class ActivityKamtibmas : AppCompatActivity() {
    private lateinit var binding: ActivityKamtibmasBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKamtibmasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imgBackKamtibmas.setOnClickListener() {
            val intent = Intent(this@ActivityKamtibmas, MainActivity::class.java)
            startActivity(intent)
        }
    }
}