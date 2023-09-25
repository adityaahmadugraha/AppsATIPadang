package com.aditya.appsatipadang.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.aditya.appsatipadang.MainActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.api.ApiConfig
import com.aditya.appsatipadang.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory(ApiConfig.getApiService())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            viewModel.performLogin(username, password) // Mengganti pemanggilan viewModel.login menjadi viewModel.performLogin
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }


        viewModel.loginResult.observe(this, Observer { result ->
            when (result) {
                is LoginResult.Success -> {
                    // Login berhasil, arahkan ke MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Optional: Menutup LoginActivity agar tidak bisa kembali
                }
                is LoginResult.Error -> {
                    // Tangani kesalahan login
                    val errorMessage = result.message
                    // Tampilkan pesan kesalahan kepada pengguna
                }
            }
        })
    }
}







