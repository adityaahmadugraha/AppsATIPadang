package com.aditya.appsatipadang.admin.ui.sarana_admin

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.data.remote.request.KirimTeknisiRequest
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject


@HiltViewModel
class SaranaAdminViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    fun getUser() = dataRepository.getUser().asLiveData()
    fun getDataLaporanId(token: String, id: String) =
        dataRepository.getLaporanId(token, id).asLiveData()
    fun getDataLaporan(token: String, id: String) =
            dataRepository.getDataLaporan(token, id).asLiveData()

    fun getTeknisiList(token: String, roles: String) =
        dataRepository.getTeknisiList(token, roles).asLiveData()


    fun getTeknisiList(token: String) = dataRepository.getTeknisiList(token).asLiveData()


    fun inputLaporan(
        token: String,
        requestBody: RequestBody
    ) = dataRepository.inputLaporan(token, requestBody).asLiveData ()


    fun inputLaporanTeknisi(
        token: String,
        kirimTeknisiRequest: KirimTeknisiRequest
    ) = dataRepository.insertLaporanTeknisi(token, kirimTeknisiRequest).asLiveData()

}

