package com.aditya.appsatipadang.laporan.sarana


import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SaranaViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    fun inputLaporan(token: String, itemLaporaneResponse: ItemLaporaneResponse) =
        repository.inputLaporan(token, itemLaporaneResponse).asLiveData()


    fun getUser() = repository.getUser().asLiveData()

}