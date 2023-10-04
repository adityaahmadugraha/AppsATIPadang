package com.aditya.appsatipadang.data.remote.network

import com.aditya.appsatipadang.data.remote.request.InputLaporanRequest
import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.remote.response.AuthResponse
import com.aditya.appsatipadang.data.remote.response.ItemLaporaneResponse
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
import retrofit2.http.PUT
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


    @GET("laporan_harian")
    suspend fun getListLaporan(
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

//    @GET("laporan_bulanan")
//    suspend fun getListLaporan(
//        @Header("Authorization") token: String,
//    ): LaporanResponse

    @POST("laporan")
    suspend fun inputLaporan(
        @Header("Authorization") token: String,
        @Body inputLaporanRequest: InputLaporanRequest
    ): LaporanResponse

@GET("getLlaporan/{id}")
suspend fun getDataLaporan(
    @Header("Authorization") token : String,
    @Path("id") id: String
):LaporanInfoResponse


}