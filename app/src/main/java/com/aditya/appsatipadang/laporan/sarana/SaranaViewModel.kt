package com.aditya.appsatipadang.laporan.sarana


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.data.remote.request.InputLaporanRequest
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SaranaViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    fun inputLaporan(token: String, inputLaporanRequest: InputLaporanRequest) =
        repository.inputLaporan(token, inputLaporanRequest).asLiveData()

    fun getUser() = repository.getUser().asLiveData()

}