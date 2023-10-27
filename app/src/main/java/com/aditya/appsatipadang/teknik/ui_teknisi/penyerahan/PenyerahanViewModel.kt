package com.aditya.appsatipadang.teknik.ui_teknisi.penyerahan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.data.remote.request.KirimTeknisiRequest
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class PenyerahanViewModel @Inject constructor(

    private val repository: DataRepository
) : ViewModel() {

    fun getUser() = repository.getUser().asLiveData()
    fun getLaporanId(
        token: String,
        id: String
    ) = repository.getLaporanId(token, id).asLiveData ()

    fun inputPenyerahan(token: String, requestBody: RequestBody) = repository.inputPenyerahan(token, requestBody).asLiveData()

}