package com.aditya.appsatipadang.user.laporan.prasarana

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.data.remote.request.InputLaporanRequest
import com.aditya.appsatipadang.data.remote.request.InputPrasaranaRequest
import com.aditya.appsatipadang.user.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class PrasaranaViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {


    fun getUser() = repository.getUser().asLiveData()
    fun inputLaporanPrasana(
        token: String,
        type: RequestBody,
        tanggal: RequestBody,
        lokasi: RequestBody,
        merk: RequestBody,
        foto: MultipartBody.Part,
    ) = repository.inputLaporanPrasana(token, type, tanggal, lokasi, merk, foto).asLiveData()



}