package com.aditya.appsatipadang.user.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: DataRepository
): ViewModel() {

    fun getUser() = repository.getUser().asLiveData()

        fun deleteUser() = viewModelScope.launch {
            repository.deleteUser()
    }

    fun updateUserProfile(
        token: String,
        foto: MultipartBody.Part? = null,
    ) = repository.updateUserProfile(token, foto).asLiveData()
}
