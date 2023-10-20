package com.aditya.appsatipadang.user.ui.lupa_password

import androidx.lifecycle.ViewModel
import com.aditya.appsatipadang.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LupaPasswordViewModel @Inject constructor(
    private val repository: DataRepository

) : ViewModel() {
//    fun lupaPassword(token: String, request: User) =
//        repository.lupaPassword(token, request).asLiveData()

}