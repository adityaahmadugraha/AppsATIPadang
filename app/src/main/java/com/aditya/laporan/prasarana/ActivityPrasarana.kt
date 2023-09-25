package com.aditya.laporan.prasarana

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.ActivityKamtibmasBinding
import com.aditya.appsatipadang.databinding.ActivityPrasaranaBinding

class ActivityPrasarana : AppCompatActivity() {

    private lateinit var binding: ActivityPrasaranaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrasaranaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}