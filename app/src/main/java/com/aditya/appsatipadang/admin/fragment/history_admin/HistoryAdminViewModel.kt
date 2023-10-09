package com.aditya.appsatipadang.admin.fragment.history_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryAdminViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
    fun getUser() = repository.getUser().asLiveData()

    fun getListLaporan(token : String) = repository.getListLaporan(token).asLiveData()

    fun getListLaporanHarian(token : String) = repository.getListLaporanHarian(token).asLiveData()
}