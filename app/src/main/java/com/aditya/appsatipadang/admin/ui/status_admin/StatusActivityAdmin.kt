package com.aditya.appsatipadang.admin.ui.status_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.databinding.ActivityStatusAdminBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StatusActivityAdmin : AppCompatActivity() {
    private lateinit var binding: ActivityStatusAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatusAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imgBack.setOnClickListener {
            intent = Intent(this@StatusActivityAdmin, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}