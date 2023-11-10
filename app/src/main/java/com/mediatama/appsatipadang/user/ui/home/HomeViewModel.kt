package com.mediatama.appsatipadang.user.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mediatama.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    fun getUser() = repository.getUser().asLiveData()

    fun getListLaporan(token: String) = repository.getListLaporan(token).asLiveData()

    fun getListLaporanHarian(token: String) = repository.getLaporanHarianTeknisi(token).asLiveData()
    fun getListLaporanBulanan(token: String) =
        repository.getLaporanBulananTeknisi(token).asLiveData()

    fun getDataUser(token: String) = repository.getDataUser(token).asLiveData()
}
