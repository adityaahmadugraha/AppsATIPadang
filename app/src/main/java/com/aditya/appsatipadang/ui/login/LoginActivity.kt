package com.aditya.appsatipadang.ui.login

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.aditya.appsatipadang.MainActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel.getUser().observe(this@LoginActivity) {

        }

        binding.apply {
            btnLogin.setOnClickListener {
                val username = txtUsername.text.toString()
                val password = txtPassword.text.toString()

                val loginRequest = LoginRequest(username, password)
                if (validateInput(loginRequest)) {
                    loginUser(loginRequest)
                }
            }
        }
    }

    private fun toMainActivity() {
        Intent(this@LoginActivity, MainActivity::class.java).also {
            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(it)
        }
    }

    private fun loginUser(loginRequest: LoginRequest) {
        binding.apply {
            viewModel.loginUser(loginRequest).observe(this@LoginActivity) { result ->
                when (result) {
                    is Resource.Loading -> {
                        setLoadingInput(true)
                    }
                    is Resource.Success -> {
                        setLoadingInput(false)
                        Log.d(ContentValues.TAG, "login Success")

                        toMainActivity()
                    }
                    is Resource.Error -> {
                        setLoadingInput(false)
                        Log.d(ContentValues.TAG, "login Error")
                        Toast.makeText(
                            applicationContext,
                            "Login Error",
                            Toast.LENGTH_SHORT
                        ).show()


                    }
                }
            }
        }
    }



    private fun setLoadingInput(condition: Boolean) {
        binding.apply {
            progressBar.isVisible = condition
            btnLogin.isEnabled = !condition
        }
    }

    private fun validateInput(loginRequest: LoginRequest): Boolean {
        binding.apply {
            if (loginRequest.username.isEmpty()) {
                tiUsername.isErrorEnabled = true
                tiUsername.error = getString(R.string.must_not_empty)
                return false
            }
            if (loginRequest.password.isEmpty()) {
                tiPassword.isErrorEnabled = true
                tiPassword.error = getString(R.string.must_not_empty)
                return false
            }
            return true
        }
    }
}
