package com.aditya.appsatipadang.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.appsatipadang.api.ApiService
import com.aditya.appsatipadang.data.LoginRequest
import com.aditya.appsatipadang.data.LoginResponse
import com.aditya.appsatipadang.data.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val apiService: ApiService) : ViewModel() {
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    // Menggunakan coroutine untuk fungsi login
    fun performLogin(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = apiService.login(LoginRequest(username, password))

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse?.status == true) {
                        _loginResult.postValue(loginResponse.user?.let { LoginResult.Success(it) })
                    } else {
                        _loginResult.postValue(LoginResult.Error("Login gagal"))
                    }
                } else {
                    _loginResult.postValue(LoginResult.Error("Terjadi kesalahan jaringan"))
                }
            } catch (e: Exception) {
                _loginResult.postValue(LoginResult.Error("Terjadi kesalahan jaringan"))
            }
        }
    }
}


sealed class LoginResult {
    data class Success(val user: User) : LoginResult()
    data class Error(val message: String) : LoginResult()
}

