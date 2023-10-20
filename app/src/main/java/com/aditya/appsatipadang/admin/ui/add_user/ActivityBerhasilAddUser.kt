package com.aditya.appsatipadang.admin.ui.add_user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.databinding.ActivityBerhasilAddUserBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ActivityBerhasilAddUser : AppCompatActivity() {

    private lateinit var binding: ActivityBerhasilAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBerhasilAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            intent = Intent(this@ActivityBerhasilAddUser, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}