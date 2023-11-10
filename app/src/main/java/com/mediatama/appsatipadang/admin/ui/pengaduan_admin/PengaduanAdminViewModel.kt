package com.mediatama.appsatipadang.admin.ui.pengaduan_admin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mediatama.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PengaduanAdminViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    fun getUser() = repository.getUser().asLiveData()

    fun getLaporanStatus(token : String, status : String) = repository.getLaporanStatus(token, status).asLiveData()

    fun getHistoryDihapus(token : String) = repository.getHistoryDihapus(token).asLiveData()





}