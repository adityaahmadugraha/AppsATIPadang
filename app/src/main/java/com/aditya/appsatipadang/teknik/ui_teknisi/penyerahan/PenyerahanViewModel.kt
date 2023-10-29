package com.aditya.appsatipadang.teknik.ui_teknisi.penyerahan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class PenyerahanViewModel @Inject constructor(

    private val repository: DataRepository
) : ViewModel() {

    fun getUser() = repository.getUser().asLiveData()


    fun getPenyerahan(token : String, status : String) = repository.getLaporanStatus(token, status).asLiveData()

//    fun getPenyerahan(token : String, status : String) = repository.getPenyerahan(token, status).asLiveData()




    fun getLaporanStatus(token : String, status : String) = repository.getLaporanStatus(token, status).asLiveData()



    fun inputPenyerahan(token: String, requestBody: RequestBody) = repository.inputPenyerahan(token, requestBody).asLiveData()

}