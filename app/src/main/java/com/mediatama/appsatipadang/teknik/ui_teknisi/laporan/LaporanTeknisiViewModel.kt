package com.mediatama.appsatipadang.teknik.ui_teknisi.laporan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mediatama.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class LaporanTeknisiViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
    fun getUser() = repository.getUser().asLiveData()
    fun inputLaporan(
        token: String,
        requestBody: RequestBody
    ) = repository.inputLaporan(token, requestBody).asLiveData ()
    fun getLaporanId(
        token: String,
        id: String
    ) = repository.getLaporanId(token, id).asLiveData ()

    fun kirimLaporanPerbaikan(token: String, requestBody: RequestBody) = repository.kirimLaporanPerbaikan(token, requestBody).asLiveData()


}