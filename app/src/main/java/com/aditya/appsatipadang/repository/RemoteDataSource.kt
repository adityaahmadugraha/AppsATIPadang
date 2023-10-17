package com.aditya.appsatipadang.repository

import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.remote.request.KirimTeknisiRequest
import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.remote.response.AddUserRequest
import com.aditya.appsatipadang.data.remote.response.LaporanIdResponse
import com.aditya.appsatipadang.data.remote.response.LaporanInfoResponse
import com.aditya.appsatipadang.data.remote.response.LaporanResponse
import com.aditya.appsatipadang.data.remote.response.ProfileUserResponse
import com.aditya.appsatipadang.network.ApiService
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
        foto: MultipartBody.Part? = null,
    ) = flow {
        emit(Resource.Loading())
        val response = apiService.updateProfile(token, foto)
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

    fun getListPengerjaan(token: String) = flow {
        emit(Resource.Loading())
        val response = apiService.getListPengerjaan(token)
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

    fun getListLaporanBulanan(token: String) = flow {
        emit(Resource.Loading())
        val response = apiService.getListLaporanBulanan(token)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)


    fun getDataLaporan(token: String, id: String) = flow<Resource<LaporanInfoResponse>> {
        emit(Resource.Loading())
        val response = apiService.getDataLaporan(token, id)
        response.let {
            if (it.status == 200) emit(Resource.Success(it))
            else emit(Resource.Error("Data Tidak Ditemukan"))
        }
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)


    fun getTeknisiList(token: String, roles: String) = flow {
        emit(Resource.Loading())
        val response = apiService.getTeknisiList(token, roles)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)


    //input gambar
    fun insertLaporan(
        token: String,
        requestBody: RequestBody
    ) = flow<Resource<LaporanResponse>> {
        emit(Resource.Loading())
        val response = apiService.insertLaporan(token, requestBody)
        response.let {
            if (it.status == 200) emit(Resource.Success(it))
            else emit(Resource.Error("Data Tidak Ditemuan"))
        }
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)


//    fun inputFotoProfil(
//        token: String,
//        requestBody: RequestBody
//    ) = flow<Resource<ProfileUserResponse>> {
//        emit(Resource.Loading())
//        val response = apiService.insertFoto(token, requestBody)
//        response.let {
//            if (it.status == 200) emit(Resource.Success(it))
//            else emit(Resource.Error("Error"))
//        }
//    }.catch {
//        emit(Resource.Error(it.message ?: ""))
//    }.flowOn(Dispatchers.IO)

    fun inputFotoProfil(
        token: String,
        userId: String, // Tambahkan parameter ID pengguna
        requestBody: MultipartBody.Part
    ) = flow<Resource<ProfileUserResponse>> {
        emit(Resource.Loading())

        val response = apiService.insertFoto(userId, token, requestBody) // Kirim ID pengguna ke server

        response.let {
            if (it.status == 200) emit(Resource.Success(it))
            else emit(Resource.Error("Error"))
        }
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)



    fun insertLaporanTeknisi(
        token: String,
        kirimTeknisiRequest: KirimTeknisiRequest
    ) = flow<Resource<LaporanResponse>> {
        emit(Resource.Loading())
        val response = apiService.insertLaporanTeknisi(token, kirimTeknisiRequest)
        response.let {
            if (it.status == 200) emit(Resource.Success(it))
            else emit(Resource.Error("Data Tidak Ditemukan"))
        }
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    fun updateLaporan(
        token: String,
        requestBody: RequestBody
    ) = flow<Resource<LaporanResponse>> {
        emit(Resource.Loading())
        val response = apiService.updateLaporan(token, requestBody)
        response.let {
            if (it.status == 200) emit(Resource.Success(it))
            else emit(Resource.Error("Data Tidak Ditemukan"))
        }
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    fun getLaporanId(
        token: String,
        id: String
    ) = flow<Resource<LaporanIdResponse>> {
        emit(Resource.Loading())
        val response = apiService.getLaporan(token, id)
        response.let {
            if (it.status == 200) emit(Resource.Success(it))
            else emit(Resource.Error("Data Tidak Ditemuan"))
        }
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

//    fun inputPrasana(
//        token: String,
//        type: RequestBody,
//        tanggal: RequestBody,
//        lokasi: RequestBody,
//        deskripsi: RequestBody,
//        foto: MultipartBody.Part,
//    ) = flow {
//        emit(Resource.Loading())
//        val response = apiService.inputPrasana(token, type, tanggal, lokasi, deskripsi, foto)
//        emit(Resource.Success(response))
//    }.catch {
//        emit(Resource.Error(it.message ?: ""))
//    }.flowOn(Dispatchers.IO)


    fun inputKamtibmas(
        token: String,
        type: RequestBody,
        lokasi: RequestBody,
        deskripsi: RequestBody,
        tanggal: RequestBody,
        waktu: RequestBody,
        foto: MultipartBody.Part,
    ) = flow {
        emit(Resource.Loading())
        val response =
            apiService.inputKamtibmas(token, type, lokasi, deskripsi, tanggal, waktu, foto)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    fun kirimLaporanPerbaian(
        token: String,
        requestBody: RequestBody
    ) = flow<Resource<LaporanResponse>> {
        emit(Resource.Loading())
        val response = apiService.kirimLaporanPerbaikan(token, requestBody)
        response.let {
            if (it.status == 200) emit(Resource.Success(it))
            else emit(Resource.Error("Data Tidak Ditemuan"))
        }
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    // teknisi

    fun getListLaporanharianTeknisi(token: String) = flow {
        emit(Resource.Loading())
        val response = apiService.getListLaporanHarian(token)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

    fun getListLaporanbulananTeknisi(token: String) = flow {
        emit(Resource.Loading())
        val response = apiService.getListLaporanBulanan(token)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)


    fun getTeknisiList(token: String) = flow {
        emit(Resource.Loading())
        val response = apiService.getTeknisiNama(token)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

//    fun addUser(token: String) = flow {
//        emit(Resource.Loading())
//        val response = apiService.addUser(token)
//        emit(Resource.Success(response))
//    }.catch {
//        emit(Resource.Error(it.message ?: ""))
//    }.flowOn(Dispatchers.IO)


    fun insertUser(token: String, request: AddUserRequest) = flow {
        emit(Resource.Loading())
        val response = apiService.addUser(token, request)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)
}