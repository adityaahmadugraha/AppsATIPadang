package com.aditya.appsatipadang.admin.ui.kamtibmas_admin

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.remote.request.AddUserResponse
import com.aditya.appsatipadang.data.remote.response.AddUserRequest
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    fun getUser() = dataRepository.getUser().asLiveData()

    fun insertUser(token: String, request: AddUserRequest) = dataRepository.insertUser(token, request).asLiveData()

//    fun insertUser(token: String, request: AddUserRequest): LiveData<Resource<AddUserResponse>> {
//        return dataRepository.insertUser(token, request).asLiveData()
//    }
}
