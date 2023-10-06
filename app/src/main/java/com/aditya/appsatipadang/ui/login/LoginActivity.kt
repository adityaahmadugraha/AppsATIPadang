package com.aditya.appsatipadang.ui.login


import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.user.MainActivity
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.local.UserLocal
import com.aditya.appsatipadang.data.remote.request.LoginRequest
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

        checkUserLogin()

//        binding.imgNext.setOnClickListener {
//            intent = Intent(this@LoginActivity, HomeActivity::class.java)
//            startActivity(intent)
//        }

        binding.btnLogin.setOnClickListener {
            loginUser()
        }

    }

    private fun loginUser() {
        val username = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()

        viewModel.loginUser(LoginRequest(username, password))
            .observe(this@LoginActivity) { result ->
                when (result) {
                    is Resource.Loading -> {
                        setInputLoading(true)
                    }

                    is Resource.Success -> {
                        setInputLoading(false)

                        if (result.data.status == 200) {

                            val userData = result.data.user
                            viewModel.saveUserLocal(
                                UserLocal(
                                    userData?.name.toString(),
                                    userData?.username.toString(),
                                    userData?.password.toString(),
                                    userData?.email.toString(),
                                    userData?.no_tlp.toString(),
                                    userData?.alamat.toString(),
                                    userData?.roles.toString(),
                                    userData?.token.toString()
                                )
                            )
                            checkUserLogin()
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
        viewModel.getUser().observe(this@LoginActivity) { userData ->
            if (userData.username.isNotEmpty() || userData.token.isNotEmpty()) {
                if (userData.roles == "Admin"){
                    Intent(this@LoginActivity, HomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(this)
                    }
                }else if (userData.roles == "Pelapor"){
                    Toast.makeText(this@LoginActivity, "Anda Berhasil Login", Toast.LENGTH_SHORT).show()
                    Intent(this@LoginActivity, MainActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(this)
                    }
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