package com.aditya.appsatipadang.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

//    fun getListLaporan(token: String) = repository.getListLaporan(token).asLiveData()

}