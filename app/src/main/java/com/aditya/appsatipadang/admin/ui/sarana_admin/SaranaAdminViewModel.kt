package com.aditya.appsatipadang.admin.ui.sarana_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.data.remote.request.KirimTeknisiRequest
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SaranaAdminViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    fun getUser() = dataRepository.getUser().asLiveData()
    fun getDataLaporanId(token: String, id: String) =
        dataRepository.getLaporanId(token, id).asLiveData()
    fun getTeknisiList(token: String) = dataRepository.getTeknisiList(token).asLiveData()

    fun inputLaporanTeknisi(
        token: String,
        kirimTeknisiRequest: KirimTeknisiRequest
    ) = dataRepository.insertLaporanTeknisi(token, kirimTeknisiRequest).asLiveData()

}

