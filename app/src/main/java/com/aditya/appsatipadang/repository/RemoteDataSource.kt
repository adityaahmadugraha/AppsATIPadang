package com.aditya.appsatipadang.repository

import com.aditya.appsatipadang.data.remote.network.ApiService
import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.remote.request.InputLaporanRequest
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
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


    fun getListLaporan(token: String) = flow {
        emit(Resource.Loading())
        val response = apiService.getListLaporan(token)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)


    fun getListLaporanHarian(token: String) = flow {
        emit(Resource.Loading())
        val response = apiService.getListLaporanHarian(token)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)


    fun inputLaporan(token: String, request: InputLaporanRequest) = flow {
        emit(Resource.Loading())
        val response = apiService.inputLaporan(token,request)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

//    fun inputLaporan(token: String, saranaData: SaranaData) = flow {
//        emit(Resource.Loading())
//        val response = apiService.inputLaporan(token, saranaData)
//        emit(Resource.Success(response))
//    }.catch {
//        emit(Resource.Error(it.message ?: ""))
//    }.flowOn(Dispatchers.IO)


//    fun getListLaporan() = flow<Resource<LaporanResponse>> {
//        emit(Resource.Loading())
//        val response = apiService.getListLaporan(token)
//        emit(Resource.Success(response))
//        response.let {
//            if (it.data!!.isNotEmpty()) emit(Resource.Success(it))
//            else emit(Resource.Error("Data Tidak Ditemukan"))
//        }
//    }.catch {
//        emit(Resource.Error(it.message ?: ""))
//    }.flowOn(Dispatchers.IO)

//    fun inputLaporan(token: String, inputLaporanRequest: InputLaporanRequest) = flow<Resouce<InputLaporanRequest>> {
//        emit(Resouce.Loading())
//        val response = apiService.inputLaporan(token, inputLaporan)
//        response.let {
//            if (it.status == 200) emit(Resouce.Success(it))
//            else emit(Resouce.Error("Data Tidak Ditemukan"))
//        }
//    }.catch {
//        Log.d(ContentValues.TAG, "dataCheck: ${it.message}")
//    }.flowOn(Dispatchers.IO)

}