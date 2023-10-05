package com.aditya.appsatipadang.admin.fragment.history_admin

import androidx.lifecycle.ViewModel
import com.aditya.appsatipadang.user.repository.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryAdminViewModel @Inject constructor(
    private val repository: DataRepository
) : ViewModel() {
}