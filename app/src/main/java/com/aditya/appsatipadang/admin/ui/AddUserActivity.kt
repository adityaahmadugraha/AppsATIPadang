package com.aditya.appsatipadang.admin.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.databinding.ActivityAddUserBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
