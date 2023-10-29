package com.aditya.appsatipadang.user.ui.login

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.local.UserLocal
import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.databinding.ActivityLoginBinding
import com.aditya.appsatipadang.supervisor.ActivitySupervisor
import com.aditya.appsatipadang.teknik.ActivityTeknik
import com.aditya.appsatipadang.user.MainActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MultipartBody
import okhttp3.RequestBody


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private var fcmToken = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        //        get token fcm
        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.i("FCM_TOKEN", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            fcmToken = task.result
        })

        //        end token fcm
        checkUserLogin()


        binding.btnLogin.setOnClickListener {
            loginUser()
        }


        binding.txtLupaPassword.setOnClickListener {
            showInputDialog(
                context = this,
                title = "Masukkan Email Akun",
                message = "Masukkan alamat email akun yang akan direset password..",
            ) { userInput ->
                val requestBody: RequestBody = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("email", userInput)
                    .build()

                viewModel.gantiPassword(requestBody).observe(this@LoginActivity) {
                    when (it) {
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            if (it.data.status == 200) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Silahkan cek alamat email",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Email yang dimasukkan tidak valid!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        is Resource.Error -> {}
                    }
                }
            }
        }
    }

    private fun showInputDialog(
        context: Context,
        title: String,
        message: String,
        onInputReceived: (String) -> Unit
    ) {
        val inputEditText = EditText(context)

        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setView(inputEditText)
            .setPositiveButton("OK") { _, _ ->
                val userInput = inputEditText.text.toString()
                onInputReceived(userInput)
            }
            .setNegativeButton("Cancel") { _, _ -> }
            .create()

        dialog.show()
    }

    private fun loginUser() {
        val username = binding.txtUsername.text.toString()
        val password = binding.txtPassword.text.toString()

        viewModel.loginUser(LoginRequest(username, password, fcmToken))
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
                                    userData?.id.toString(),
                                    userData?.name.toString(),
                                    userData?.username.toString(),
                                    userData?.email.toString(),
                                    userData?.no_telp.toString(),
                                    userData?.password.toString(),
                                    userData?.roles.toString(),
                                    userData?.alamat.toString(),
                                    userData?.foto.toString(),
                                    userData?.token.toString(),
                                    userData?.fcmtoken.toString(),
                                )
                            )

                            checkUserLogin()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Username atau password salah",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    is Resource.Error -> {
                        setInputLoading(false)
                        Toast.makeText(
                            this@LoginActivity,
                            "Terjadi kesalahan saat login",
                            Toast.LENGTH_SHORT
                        ).show()
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
                if (userData.roles == "Supervisor") {
                    Toast.makeText(this@LoginActivity, "Anda Berhasil Login", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("LOGINADMIN:::::", userData.token)
                    Intent(this@LoginActivity, HomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(this)
                    }
                } else if (userData.roles == "Pelapor") {
                    showConfirmDialog(userData)
                } else if (userData.roles == "Teknisi") {
                    Toast.makeText(this@LoginActivity, "Anda Berhasil Login", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("LOGINTEKNISI:::::", userData.token)
                    Intent(this@LoginActivity, ActivityTeknik::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(this)
                    }
                } else if (userData.roles == "Pimpinan") {
                    Toast.makeText(this@LoginActivity, "Anda Berhasil Login", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("LOGINSPP:::::", userData.token)
                    Intent(this@LoginActivity, ActivitySupervisor::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(this)
                    }
                }
            }
        }
    }

    private fun showConfirmDialog(userData: UserLocal) {
        val dialogView = layoutInflater.inflate(R.layout.custom_alertdialog, null)

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setTitle("Konfirmasi Login")
            .setPositiveButton("Lanjutkan") { dialog, _ ->
                dialog.dismiss()
                if (userData.roles == "Pelapor") {
                    viewModel.saveUserLocal(
                        UserLocal(
                            userData.id,
                            userData.name,
                            userData.username,
                            userData.email,
                            userData.no_telp,
                            userData.password,
                            userData.roles,
                            userData.alamat,
                            userData.foto,
                            userData.token,
                            userData.fcmtoken,
                        )
                    )
                    moveToMainActivity()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        alertDialog.show()
    }

    private fun moveToMainActivity() {
        Intent(this@LoginActivity, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
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
