package com.aditya.appsatipadang.user.laporan.kamtibmas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.data.remote.request.InputKamtibmasRequest
import com.aditya.appsatipadang.data.remote.request.InputLaporanRequest
import com.aditya.appsatipadang.data.remote.request.InputPrasaranaRequest
import com.aditya.appsatipadang.user.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class KamtibmasViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
    fun inputLaporan(token: String, inputLaporanRequest: InputLaporanRequest) =
        repository.inputLaporan(token, inputLaporanRequest).asLiveData()


    fun inputLaporanKamtibmas(token: String, inputKamtibmasRequest: InputKamtibmasRequest) =
        repository.inputLaporanKamtibmas(token, inputKamtibmasRequest).asLiveData()

    fun getUser() = repository.getUser().asLiveData()

}