package com.aditya.appsatipadang.ui.pemberitahuan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.databinding.ActivityPemberitahuanBinding
import com.aditya.appsatipadang.ui.detailstatuslaporan.DetailStatusLaporanActivity

class ActivityPemberitahuan : AppCompatActivity() {

    private lateinit var binding: ActivityPemberitahuanBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPemberitahuanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()


        binding.btnCekLaporan.setOnClickListener {
            intent = Intent(this@ActivityPemberitahuan, DetailStatusLaporanActivity::class.java)
            startActivity(intent)
        }


    }
}