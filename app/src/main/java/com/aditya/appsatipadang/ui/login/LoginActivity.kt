package com.aditya.appsatipadang.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.aditya.appsatipadang.MainActivity
import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.local.UserLocal
import com.aditya.appsatipadang.databinding.ActivityLoginBinding
import com.google.android.material.textfield.TextInputEditText
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



        binding.btnLogin.setOnClickListener {
            loginUser()
        }

        checkUserLogin()
    }

    private fun loginUser() {
        val email = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()

        // Validasi input
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this@LoginActivity, "Username dan password harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        viewModel.loginUser(LoginRequest(email, password)).observe(this@LoginActivity) { result ->
            when (result) {
                is Resource.Loading -> {
                    setInputLoading(true)
                }

                is Resource.Success -> {
                    setInputLoading(false)

                    Log.d(TAG, "loginUser: ${result.data.body()}")

                    if (result.data.code() == 200) {

                        val userData = result.data.body()
                        if (userData != null) {
                            viewModel.saveUser(
                                UserLocal(
                                    // ?: 1 pengiriman data jika null maka akan mengambil 1
                                    userData.user?.id,
                                    userData.user?.name.toString(),
                                    userData.user?.username.toString(),
                                    userData.user?.password.toString(),
                                    userData.user?.roles.toString(),
                                    userData.user?.created_at.toString(),
                                    userData.user?.update_at.toString()
                                )
                            )

                            checkUserLogin()

                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Data pengguna tidak valid",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Password atau username salah!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                is Resource.Error -> {
                    setInputLoading(false)
                    Toast.makeText(this@LoginActivity, result.error, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }




    private fun setInputLoading(condition: Boolean) {
        binding.apply {
            btnLogin.isEnabled = !condition
            progressBar.isVisible = condition
        }
    }

    private fun checkUserLogin() {
        viewModel.getUser().observe(this@LoginActivity) {
            if (it.username.isNotEmpty() || it.password.isNotEmpty()) {
                Intent(this@LoginActivity, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(this)
                }
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is TextInputEditText || v is AutoCompleteTextView) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

}