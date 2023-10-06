package com.aditya.appsatipadang.admin.ui.prasarana_admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.databinding.ActivityPrasaranaAdminBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrasaranaActivityAdmin : AppCompatActivity() {

    private lateinit var binding: ActivityPrasaranaAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPrasaranaAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imgBack.setOnClickListener {
            intent = Intent(this@PrasaranaActivityAdmin, HomeActivity::class.java)
            startActivity(intent)
        }
    }
}