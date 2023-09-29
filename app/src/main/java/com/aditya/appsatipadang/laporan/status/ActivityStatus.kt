package com.aditya.appsatipadang.laporan.status

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.MainActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.ActivitySaranaBinding
import com.aditya.appsatipadang.databinding.ActivityStatusBinding

class ActivityStatus : AppCompatActivity() {

    private lateinit var binding: ActivityStatusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imgBackStatusLaporan.setOnClickListener {
            intent = Intent(this@ActivityStatus, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            intent = Intent(this@ActivityStatus, MainActivity::class.java)
            startActivity(intent)
        }
    }
}