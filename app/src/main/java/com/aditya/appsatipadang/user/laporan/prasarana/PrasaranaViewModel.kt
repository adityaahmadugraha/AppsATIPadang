package com.aditya.appsatipadang.user.laporan.prasarana

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.data.remote.request.InputLaporanRequest
import com.aditya.appsatipadang.data.remote.request.InputPrasaranaRequest
import com.aditya.appsatipadang.user.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrasaranaViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

//    fun inputLaporan(token: String, inputLaporanRequest: InputLaporanRequest) =
//        repository.inputLaporan(token, inputLaporanRequest).asLiveData()

    fun inputLaporanPrasana(token: String, inputPrasaranaRequest: InputPrasaranaRequest) =
        repository.inputLaporanPrasana(token, inputPrasaranaRequest).asLiveData()

    fun getUser() = repository.getUser().asLiveData()

}