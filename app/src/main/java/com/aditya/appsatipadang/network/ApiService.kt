package com.aditya.appsatipadang.network

import com.aditya.appsatipadang.data.remote.request.InputLaporanRequest
import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.remote.response.AuthResponse
import com.aditya.appsatipadang.data.remote.response.LaporanInfoResponse
import com.aditya.appsatipadang.data.remote.response.LaporanResponse
import com.aditya.appsatipadang.data.remote.response.ProfileUserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @GET("getUser")
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): ProfileUserResponse

    @Multipart
    @POST("profile/update")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part? = null,
        @Part("email") email: RequestBody,
        @Part("fullname") fullname: RequestBody,
    ): ProfileUserResponse


    @GET("laporan")
    suspend fun getListLaporan(
        @Header("Authorization") token: String,
    ): LaporanResponse


    @GET("laporan")
    suspend fun getListPengerjaan(
        @Header("Authorization") token: String,
    ): LaporanResponse

    @GET("laporan_harian")
    suspend fun getListLaporanHarian(
        @Header("Authorization") token: String,
    ): LaporanResponse

    @GET("laporan_bulanan")
    suspend fun getListLaporanBulanan(
        @Header("Authorization") token: String,
    ): LaporanResponse


    //inout sarana
    @POST("laporan")
    suspend fun insertLaporan(
        @Header("Authorization") token: String,
        @Body body : RequestBody
    ): LaporanResponse

    @GET("getUser")
    suspend fun getTeknisiList(
        @Header("Authorization") token: String
    ): List<String>


//    @GET("laporan_tahun")
//    suspend fun getListLaporanTahun(
//        @Header("Authorization") token: String,
//    ): LaporanResponse

//    @GET("laporan_bulanan")
//    suspend fun getListLaporan(
//        @Header("Authorization") token: String,
//    ): LaporanResponse


    @GET("getlaporan/{id}")
    suspend fun getDataLaporan(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): LaporanInfoResponse


    @Multipart
    @POST("laporan")
    suspend fun inputPrasana(
        @Header("Authorization") token: String,
        @Part("type") type: RequestBody,
        @Part("tanggal") tanggal: RequestBody,
        @Part("lokasi") lokasi: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part foto: MultipartBody.Part,
    ): LaporanResponse


    @Multipart
    @POST("laporan")
    suspend fun inputKamtibmas(
        @Header("Authorization") token: String,
        @Part("type") type: RequestBody,
        @Part("lokasi") lokasi: RequestBody,
        @Part("deskripsi") deskripsi: RequestBody,
        @Part("tanggal") tanggal: RequestBody,
        @Part("waktu") waktu: RequestBody,
        @Part foto: MultipartBody.Part,
    ): LaporanResponse

        @POST("laporan(id)")
    suspend fun insertTeknisi(
        @Header("Authorization") token: String,
    ): LaporanResponse





}