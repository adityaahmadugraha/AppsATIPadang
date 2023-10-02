package com.aditya.appsatipadang.repository


import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.local.UserPreference
import com.aditya.appsatipadang.data.local.UserLocal
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val remoteData: RemoteDataSource,
    private val localData: UserPreference
) {
    fun loginUser(request: LoginRequest) = remoteData.loginUser(request)

    fun getUser() = localData.getUser()
    suspend fun saveUser(userLocal: UserLocal) = localData.saveUser(userLocal)

    suspend fun deleteUser() = localData.deleteUser()

    fun getUserProfile(token: String) = remoteData.getUserProfile(token)

    fun updateUserProfile(

        token: String,
        image: MultipartBody.Part? = null,
        email: RequestBody,
        fullname: RequestBody,
    ) = remoteData.updateUserProfile(token, image, email, fullname)

    fun getListLaporan(token : String) = remoteData.getListLaporan(token)

    fun inputLaporan(token: String, request: ItemLaporaneResponse)
    = remoteData.inputLaporan(token,request)


}