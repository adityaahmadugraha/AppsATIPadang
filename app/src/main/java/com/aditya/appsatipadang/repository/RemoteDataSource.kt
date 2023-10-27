package com.aditya.appsatipadang.repository

import com.aditya.appsatipadang.data.Resource
import com.aditya.appsatipadang.data.remote.request.KirimTeknisiRequest
import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.remote.request.PenyerahanTeknisiRequest
import com.aditya.appsatipadang.data.remote.response.AddUserRequest
import com.aditya.appsatipadang.data.remote.response.LaporanIdResponse
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
        val response = apiService.getDataProfile(token)
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

    fun getLaporanStatus(token: String, status : String) = flow {
        emit(Resource.Loading())
        val response = apiService.getLaporanStatus(token, status)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)


//    fun deleteLaporan(token: String, id : String) = flow {
//        emit(Resource.Loading())
//        val response = apiService.deletelaporan(token, id)
//        emit(Resource.Success(response))
//    }.catch {
//        emit(Resource.Error(it.message ?: ""))
//    }.flowOn(Dispatchers.IO)


    fun deleteLaporan(token: String, id: String) = flow {
        try {
            emit(Resource.Loading())
            val response = apiService.deletelaporan(token, id)

            if (response.status == 200) {
                emit(Resource.Success(response))
            } else {
                emit(Resource.Error("Gagal menghapus laporan: ${response.message}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Terjadi kesalahan dalam penghapusan laporan"))
        }
    }.flowOn(Dispatchers.IO)



//    fun deleteLaporan(
//        token: String,
//        id: String
//    ) = flow<Resource<LaporanResponse>> {
//        emit(Resource.Loading())
//        val response = apiService.deletelaporan(token, id)
//        response.let {
//            if (it.status == 200) emit(Resource.Success(it))
//            else emit(Resource.Error("Data Tidak Ditemuan"))
//        }
//    }.catch {
//        emit(Resource.Error(it.message ?: ""))
//    }.flowOn(Dispatchers.IO)



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





    fun inputFotoProfil(
        token: String,
        requestBody: RequestBody
    ) = flow<Resource<ProfileUserResponse>> {
        emit(Resource.Loading())
        val response = apiService.insertFoto(token, requestBody)
        response.let {
            if (it.status == 200) emit(Resource.Success(it))
            else emit(Resource.Error("Data Tidak Ditemuan"))
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


    fun inputPenyerahan(
        token: String,
        requestBody: RequestBody
    ) = flow<Resource<PenyerahanTeknisiRequest>> {
        emit(Resource.Loading())
        apiService.inputPenyerahan(token, requestBody)
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

    fun insertUser(token: String, request: AddUserRequest) = flow {
        emit(Resource.Loading())
        val response = apiService.addUser(token, request)
        emit(Resource.Success(response))
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)
    fun gantiPassword(
        requestBody: RequestBody
    ) = flow<Resource<LaporanResponse>> {
        emit(Resource.Loading())
        val response = apiService.gantiPassword(requestBody)
        response.let {
            if (it.status == 200) emit(Resource.Success(it))
            else emit(Resource.Error("Data Tidak Ditemuan"))
        }
    }.catch {
        emit(Resource.Error(it.message ?: ""))
    }.flowOn(Dispatchers.IO)

}