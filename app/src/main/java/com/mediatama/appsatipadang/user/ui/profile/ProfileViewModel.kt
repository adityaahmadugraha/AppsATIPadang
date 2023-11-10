package com.mediatama.appsatipadang.user.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mediatama.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    fun getUser() = repository.getUser().asLiveData()

    fun deleteUser() = viewModelScope.launch {
        repository.deleteUser()
    }
    fun insertFoto(
        token: String,
        requestBody: RequestBody
    ) = repository.insertFoto(token, requestBody).asLiveData ()

    fun getDataUser(token: String) = repository.getDataUser(token).asLiveData()


}

