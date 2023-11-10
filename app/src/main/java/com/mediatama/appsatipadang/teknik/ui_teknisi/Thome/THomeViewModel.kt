package com.mediatama.appsatipadang.teknik.ui_teknisi.Thome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mediatama.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class THomeViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    fun getUser() = repository.getUser().asLiveData()

    fun getListLaporan(token : String) = repository.getListLaporan(token).asLiveData()

    fun getListPengerjaan(token : String) = repository.getListPengerjaan(token).asLiveData()

    fun getDataUser(token: String) = repository.getDataUser(token).asLiveData()



}