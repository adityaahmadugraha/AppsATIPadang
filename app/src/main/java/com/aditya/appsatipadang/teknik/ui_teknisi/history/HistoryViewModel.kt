package com.aditya.appsatipadang.teknik.ui_teknisi.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
    fun getUser() = repository.getUser().asLiveData()

    fun getListLaporanHarian(token : String) = repository.getLaporanHarianTeknisi(token).asLiveData()
    fun getListLaporanBulanan(token : String) = repository.getLaporanBulananTeknisi(token).asLiveData()
}