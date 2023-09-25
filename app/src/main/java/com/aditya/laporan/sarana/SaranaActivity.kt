package com.aditya.laporan.sarana

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.MainActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.ActivitySaranaBinding

class SaranaActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaranaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySaranaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imgBack.setOnClickListener() {
            val intent = Intent(this@SaranaActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}