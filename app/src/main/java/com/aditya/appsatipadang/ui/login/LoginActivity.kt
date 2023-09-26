package com.aditya.appsatipadang.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.aditya.appsatipadang.MainActivity
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

        supportActionBar?.hide()

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.txtPassword.text.toString()

            viewModel.performLogin(username, password)
        }

        viewModel.loginResult.observe(this, Observer { result ->
            when (result) {
                is LoginResult.Success -> {

                    val user = result.user
                    showToast("Login berhasil")
                    navigateToMainActivity()
                }

                is LoginResult.Error -> {
                    val errorMessage = result.message
                    showToast("Login gagal: $errorMessage")
                }
            }
        })
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}