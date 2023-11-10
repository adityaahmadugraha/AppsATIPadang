package com.mediatama.appsatipadang.user.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mediatama.appsatipadang.data.remote.request.LoginRequest
import com.mediatama.appsatipadang.data.local.UserLocal
import com.mediatama.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
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

    fun gantiPassword(requestBody: RequestBody) = dataRepository.gantiPassword(requestBody).asLiveData()
}
