package com.aditya.appsatipadang.repository

import com.aditya.appsatipadang.data.remote.network.ApiService
import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
) {
    fun loginUser(request: LoginRequest) = flow {
        emit(Resource.Loading())
        val response = apiService.login(request)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    fun getUserProfile(token: String) = flow {
        emit(Resource.Loading())
        val response = apiService.getProfile(token)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)


    fun updateUserProfile(
        token: String,
        image: MultipartBody.Part? = null,
        email: RequestBody,
        fullname: RequestBody,
    ) = flow {
        emit(Resource.Loading())
        val response = apiService.updateProfile(token, image, email, fullname)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)
}