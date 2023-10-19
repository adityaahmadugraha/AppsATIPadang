package com.aditya.appsatipadang.user.laporan.kamtibmas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class KamtibmasViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    fun getUser() = repository.getUser().asLiveData()

    fun inputLaporan(
        token: String,
        requestBody: RequestBody
    ) = repository.inputLaporan(token, requestBody).asLiveData ()


}