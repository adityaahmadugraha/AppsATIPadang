package com.aditya.appsatipadang.user.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.local.UserLocal
import com.aditya.appsatipadang.user.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {
    fun loginUser(loginRequest: LoginRequest) = dataRepository.loginUser(loginRequest).asLiveData()

    fun getUser() = dataRepository.getUser().asLiveData()

    fun saveUserLocal(userLocal: UserLocal) = viewModelScope.launch {
        dataRepository.saveUser(userLocal)
    }
}
