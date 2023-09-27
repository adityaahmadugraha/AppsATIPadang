package com.aditya.appsatipadang.repository

import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.local.UserPreference
import com.aditya.appsatipadang.data.local.UserLocal
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val remoteData: RemoteDataSource,
    private val localData: UserPreference
) {
    fun loginUser(request: LoginRequest) = remoteData.loginUser(request)

    fun getUser() = localData.getUser()
    suspend fun saveUser(userLocal: UserLocal) = localData.saveUser(userLocal)

    suspend fun deleteUser() = localData.deleteUser()


}