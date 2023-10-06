package com.aditya.appsatipadang.admin.ui.kamtibmas_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.databinding.ActivityKamtibmasAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KamtibmasActivityAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityKamtibmasAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityKamtibmasAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imgBack.setOnClickListener {
            intent = Intent(this@KamtibmasActivityAdmin, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}