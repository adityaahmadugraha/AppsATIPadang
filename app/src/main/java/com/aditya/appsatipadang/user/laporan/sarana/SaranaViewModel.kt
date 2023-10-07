package com.aditya.appsatipadang.user.laporan.sarana


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.data.remote.request.InputLaporanRequest
import com.aditya.appsatipadang.user.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class SaranaViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

//    fun inputLaporan(token: String, inputLaporanRequest: InputLaporanRequest) =
//        repository.inputLaporan(token, inputLaporanRequest).asLiveData()

    fun getUser() = repository.getUser().asLiveData()

    fun inputLaporan(
        token: String,
        type: RequestBody,
        tanggal: RequestBody,
        lokasi: RequestBody,
        merk: RequestBody,
        foto: MultipartBody.Part,
    ) = repository.inputLaporan(token, type, tanggal, lokasi, merk, foto).asLiveData ()


}