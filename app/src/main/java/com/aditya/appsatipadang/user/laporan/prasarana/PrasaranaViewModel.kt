package com.aditya.appsatipadang.user.laporan.prasarana

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PrasaranaViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {

    fun getUser() = repository.getUser().asLiveData()

}