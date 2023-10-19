package com.aditya.appsatipadang.admin.ui.kamtibmas_admin


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.aditya.appsatipadang.R
import com.aditya.appsatipadang.admin.HomeActivity
import com.aditya.appsatipadang.data.remote.response.AddUserRequest
import com.aditya.appsatipadang.databinding.ActivityAddUserBinding
import com.aditya.appsatipadang.user.ui.pemberitahuan.ActivityPemberitahuan
import com.aditya.appsatipadang.utils.Constant.getToken
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding

    private val viewModel: AddUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener {
            intent = Intent(this@AddUserActivity, HomeActivity::class.java)
            startActivity(intent)
        }

        val adapterRoles = ArrayAdapter.createFromResource(
            this, R.array.pilih_roles,
            android.R.layout.simple_spinner_item
        )
        adapterRoles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinerRoles.adapter = adapterRoles


        binding.btnRegister.setOnClickListener {
            val name = binding.txtName.text.toString()
            val username = binding.txtUsername.text.toString()
            val email = binding.txtEmail.text.toString()
            val no_telp = binding.txtOTelp.text.toString()
            val password = binding.txtPassword.text.toString()
            val roles = binding.spinerRoles.selectedItem.toString()
            val alamat = binding.txtAlamat.text.toString()

            if (isiSeluruhField(name, username, email, no_telp, password, roles, alamat)) {
                if (spinerValidasi(roles)) {
                    val emailLayout = binding.tiEmail
                    val passwordLayout = binding.tiPassword

                    if (isEmailValid(email)) {
                        emailLayout.error = null

                        if (isPasswordValid(password)) {
                            passwordLayout.error = null

                            val request = AddUserRequest(
                                name, username, email, no_telp, password, roles, alamat
                            )

                            viewModel.getUser().observe(this@AddUserActivity) {
                                viewModel.insertUser(it.getToken, request).observe(this@AddUserActivity) { response ->
                                    if (response != null) {
                                        Log.d("AddUserActivity", "Data pengguna berhasil ditambahkan")
                                        val intent = Intent(this, ActivityBerhasilAddUser::class.java)
                                        startActivity(intent)
                                    } else {
                                        Log.e("AddUserActivity", "Gagal menambahkan pengguna ke database")
                                        Toast.makeText(this, "Gagal menambahkan pengguna", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        } else {
                            passwordLayout.error = "Password harus terdiri minimal 6 karakter dan memiliki huruf kapital, huruf kecil, dan angka."
                        }
                    } else {
                        emailLayout.error = "Format email tidak valid"
                    }
                } else {
                    Toast.makeText(this, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun isiSeluruhField(vararg fields: String): Boolean {
        return fields.all { it.isNotEmpty() }
    }

    private fun spinerValidasi(vararg items: String): Boolean {
        return items.all { spinerItemValidasi(it) }
    }

    private fun spinerItemValidasi(item: String): Boolean {
        return item != "Pilih Roles"
    }

    private fun isEmailValid(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@gmail\\.com"

        val isEmailValid = email.matches(emailPattern.toRegex())

        if (!isEmailValid) {
            binding.tiEmail.error = getString(R.string.email_not_valid)
        } else {
            binding.tiEmail.error = null
        }

        return isEmailValid
    }

    private fun isPasswordValid(password: String): Boolean {
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d@#\$%^&+=]{6,}\$"
        val isPasswordValid = password.matches(passwordPattern.toRegex())

        val passwordLayout = binding.tiPassword

        if (!isPasswordValid) {
            passwordLayout.error = "Password harus terdiri minimal 6 karakter dan memiliki huruf kapital, huruf kecil, dan angka."
            passwordLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        } else {
            passwordLayout.error = null
            passwordLayout.endIconMode = TextInputLayout.END_ICON_PASSWORD_TOGGLE
        }

        return isPasswordValid
    }


}
