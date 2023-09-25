package com.aditya.laporan.status

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}