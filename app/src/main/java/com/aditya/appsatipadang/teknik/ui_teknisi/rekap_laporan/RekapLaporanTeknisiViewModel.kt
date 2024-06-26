package com.aditya.appsatipadang.teknik.ui_teknisi.rekap_laporan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RekapLaporanTeknisiViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel()
{
    fun getUser() = repository.getUser().asLiveData()

    fun getListLaporan(token : String) = repository.getListLaporan(token).asLiveData()
}