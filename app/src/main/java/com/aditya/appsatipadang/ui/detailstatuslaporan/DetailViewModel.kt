package com.aditya.appsatipadang.ui.detailstatuslaporan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.user.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    fun getUser() = dataRepository.getUser().asLiveData()
    fun getDataLaporan(token : String,id : String) = dataRepository.getDataLaporan(token,id).asLiveData()
}