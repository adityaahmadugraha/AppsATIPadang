package com.aditya.appsatipadang.user.laporan.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class StatusViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    fun getUser() = repository.getUser().asLiveData()
    fun getListLaporan(token : String) = repository.getListLaporan(token).asLiveData()


}