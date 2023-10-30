package com.aditya.appsatipadang.teknik.ui_teknisi.detailpenyerahan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ViewModelDetailPenyerahan @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    fun getUser() = dataRepository.getUser().asLiveData()
    fun getListPenyerahan(token: String) = dataRepository.getListPenyerahan(token).asLiveData()

}
