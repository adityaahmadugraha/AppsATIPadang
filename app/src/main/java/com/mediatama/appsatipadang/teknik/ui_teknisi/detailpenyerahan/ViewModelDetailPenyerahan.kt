package com.mediatama.appsatipadang.teknik.ui_teknisi.detailpenyerahan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mediatama.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ViewModelDetailPenyerahan @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    fun getUser() = dataRepository.getUser().asLiveData()
    fun getListPenyerahan(token: String) = dataRepository.getListPenyerahan(token).asLiveData()

    fun getDataLaporanId(token: String, id: String) =
        dataRepository.getLaporanId(token, id).asLiveData()


    fun deletePenyerahan(token: String, id : String) = dataRepository.deletePenyerahan(token,id).asLiveData()


}
