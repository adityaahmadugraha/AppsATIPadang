package com.aditya.appsatipadang.user.ui.lupa_password

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.databinding.ActivityLupaPasswordBinding
import com.aditya.appsatipadang.user.ui.login.LoginActivity

class ActivityLupaPassword : AppCompatActivity() {

    private val viewModel : LupaPasswordViewModel by viewModels()

    private lateinit var binding: ActivityLupaPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLupaPasswordBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            intent = Intent(this@ActivityLupaPassword, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}