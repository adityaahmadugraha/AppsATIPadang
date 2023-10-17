package com.aditya.appsatipadang.user.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    fun getUser() = repository.getUser().asLiveData()

    fun getListLaporan(token : String) = repository.getListLaporan(token).asLiveData()

 fun getListLaporanHarian(token : String) = repository.getLaporanHarianTeknisi(token).asLiveData()
    fun getListLaporanBulanan(token : String) = repository.getLaporanBulananTeknisi(token).asLiveData()


    fun getDataLaporan(token : String,id : String) = repository.getDataLaporan(token,id).asLiveData()
}
