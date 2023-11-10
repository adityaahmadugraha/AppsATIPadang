package com.mediatama.appsatipadang.teknik.ui_teknisi.penyerahan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mediatama.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class PenyerahanViewModel @Inject constructor(

    private val repository: DataRepository
) : ViewModel() {

    fun getUser() = repository.getUser().asLiveData()




    fun getListPenyerahan(token: String) = repository.getListPenyerahan(token).asLiveData()


    fun getDataUser(token: String) = repository.getDataUser(token).asLiveData()



    fun deletePenyerahan(token: String, id : String) = repository.deletePenyerahan(token,id).asLiveData()



    fun getNoPelaporan(token: String) = repository.getNoPelaporan(token).asLiveData()


    fun getLaporanStatus(token : String, status : String) = repository.getLaporanStatus(token, status).asLiveData()


    fun inputPenyerahan(token: String, requestBody: RequestBody) = repository.inputPenyerahan(token, requestBody).asLiveData()

}