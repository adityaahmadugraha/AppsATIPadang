package com.aditya.appsatipadang.network

import android.view.View
import com.aditya.appsatipadang.data.local.UserPreference
import com.aditya.appsatipadang.data.remote.request.AddUserResponse
import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.remote.response.AddUserRequest
import com.aditya.appsatipadang.data.remote.response.AuthResponse
import com.aditya.appsatipadang.data.remote.response.LaporanIdResponse
import com.aditya.appsatipadang.data.remote.response.LaporanInfoResponse
import com.aditya.appsatipadang.data.remote.response.LaporanResponse
import com.aditya.appsatipadang.data.remote.response.ProfileUserResponse
import com.aditya.appsatipadang.data.remote.response.TeknisiReponse
import com.aditya.appsatipadang.data.remote.response.TeknisiUserResponse
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
    ): TeknisiUserResponse

    @GET("getUser/{roles}")
    suspend fun getTeknisiList(
        @Header("Authorization") token: String,
        @Path("roles") roles: String
    ): LaporanResponse

    @Multipart
    @POST("profile/update")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part? = null,
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


    //input sarana
    @POST("laporan")
    suspend fun insertLaporan(
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): LaporanResponse


    @POST("laporan")
    suspend fun insertLaporanTeknisi(
        @Header("Authorization") token: String,
        @Body body: View
    ): LaporanResponse

    @POST("laporan")
    suspend fun updateLaporan(
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): LaporanResponse

    @GET("laporan-id/{id}")
    suspend fun getLaporan(
        @Header("Authorization") token: String,
        @Path("id") id : String
    ): LaporanIdResponse

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

    @POST("kirimlaporan")
    suspend fun kirimLaporanPerbaikan(
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): LaporanResponse

//        @POST("laporan(id)")
//    suspend fun insertTeknisi(
//        @Header("Authorization") token: String,
//    ): LaporanResponse



    @POST("laporan(id)")
    suspend fun insertTeknisi(
        @Header("Authorization") token: String,
    ): LaporanResponse


    @GET("getnamateknisi")
    suspend fun getTeknisiNama(
        @Header("Authorization") token: String,
    ): TeknisiReponse


    @POST("addUser")
    suspend fun addUser(
        @Header("Authorization") token: String,
        @Body request : AddUserRequest
    ): AddUserResponse


}