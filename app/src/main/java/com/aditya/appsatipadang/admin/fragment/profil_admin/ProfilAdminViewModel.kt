package com.aditya.appsatipadang.admin.fragment.profil_admin

import androidx.lifecycle.ViewModel
import com.aditya.appsatipadang.user.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfilAdminViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
}