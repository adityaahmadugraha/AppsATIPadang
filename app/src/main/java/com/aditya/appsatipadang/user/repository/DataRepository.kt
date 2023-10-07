package com.aditya.appsatipadang.user.repository


import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.local.UserPreference
import com.aditya.appsatipadang.data.local.UserLocal
import com.aditya.appsatipadang.data.remote.request.InputKamtibmasRequest
import com.aditya.appsatipadang.data.remote.request.InputLaporanRequest
import com.aditya.appsatipadang.data.remote.request.InputPrasaranaRequest
import com.google.gson.annotations.SerializedName
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

    fun getListLaporan(token: String) = remoteData.getListLaporan(token)

    fun getListLaporanHarian(token: String) = remoteData.getListLaporanHarian(token)

    fun getListLaporanBulanan(token: String) = remoteData.getListLaporanBulanan(token)

//    fun inputLaporan(token: String, inputLaporanRequest: InputLaporanRequest) = remoteData.inputLaporan(token,inputLaporanRequest)


    fun inputLaporan(
        token: String,
        type: RequestBody,
        tanggal: RequestBody,
        lokasi: RequestBody,
        merk: RequestBody,
        deskripsi: RequestBody,
        foto: MultipartBody.Part,
    ) = remoteData.insertLaporan(token, type, tanggal, lokasi, merk, deskripsi, foto)


    fun inputLaporanPrasana(
        token: String,
        type: RequestBody,
        tanggal: RequestBody,
        lokasi: RequestBody,
        deskripsi: RequestBody,
        foto: MultipartBody.Part,
    ) = remoteData.insertLaporanPrasana(token, type, tanggal, lokasi, deskripsi, foto)

    fun inputLaporanKamtibmas(
        token: String,
        type: RequestBody,
        lokasi: RequestBody,
        deskripsi: RequestBody,
        tanggal: RequestBody,
        waktu: RequestBody,
        foto: MultipartBody.Part,
    ) = remoteData.inputLaporanKamtibmas(token, type, lokasi, deskripsi, tanggal, waktu, foto)


    fun getDataLaporan(token: String, id: String) = remoteData.getDataLaporan(token, id)


}