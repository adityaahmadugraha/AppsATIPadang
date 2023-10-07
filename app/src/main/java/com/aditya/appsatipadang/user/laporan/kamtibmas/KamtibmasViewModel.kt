package com.aditya.appsatipadang.user.laporan.kamtibmas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.data.remote.request.InputKamtibmasRequest
import com.aditya.appsatipadang.data.remote.request.InputLaporanRequest
import com.aditya.appsatipadang.data.remote.request.InputPrasaranaRequest
import com.aditya.appsatipadang.user.repository.DataRepository
import com.google.gson.annotations.SerializedName
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class KamtibmasViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
//    fun inputLaporan(token: String, inputLaporanRequest: InputLaporanRequest) =
//        repository.inputLaporan(token, inputLaporanRequest).asLiveData()


//    fun inputLaporanKamtibmas(token: String, inputKamtibmasRequest: InputKamtibmasRequest) =
//        repository.inputLaporanKamtibmas(token, inputKamtibmasRequest).asLiveData()

    fun getUser() = repository.getUser().asLiveData()

    fun inputLaporanKamtibmas(
        token: String,
        type: RequestBody,
        lokasi: RequestBody,
        deskripsi: RequestBody,
        tanggal: RequestBody,
        waktu: RequestBody,
        foto: MultipartBody.Part,
    ) = repository.inputLaporanKamtibmas(token, type, lokasi, deskripsi, tanggal, waktu, foto).asLiveData()


}