package com.mediatama.appsatipadang.network

import com.mediatama.appsatipadang.data.remote.request.AddUserResponse
import com.mediatama.appsatipadang.data.remote.request.DetailPenyerahanResponse
import com.mediatama.appsatipadang.data.remote.request.KirimTeknisiRequest
import com.mediatama.appsatipadang.data.remote.request.LoginRequest
import com.mediatama.appsatipadang.data.remote.request.ResponsePenyerahan
import com.mediatama.appsatipadang.data.remote.response.AddUserRequest
import com.mediatama.appsatipadang.data.remote.response.AuthResponse
import com.mediatama.appsatipadang.data.remote.response.DataUserResponse
import com.mediatama.appsatipadang.data.remote.response.LaporanIdResponse
import com.mediatama.appsatipadang.data.remote.response.LaporanResponse
import com.mediatama.appsatipadang.data.remote.response.NoPelaporanTeknisi
import com.mediatama.appsatipadang.data.remote.response.PenyerahanResponse
import com.mediatama.appsatipadang.data.remote.response.ProfileUserResponse
import com.mediatama.appsatipadang.data.remote.response.TeknisiReponse
import com.mediatama.appsatipadang.data.remote.response.TeknisiUserResponse
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

    @GET("getDataUser")
    suspend fun getDataProfile(
        @Header("Authorization") token: String
    ): DataUserResponse

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

    @GET("laporan-status/{status}")
    suspend fun getLaporanStatus(
        @Header("Authorization") token: String,
        @Path("status") status: String
    ): LaporanResponse


    //delete laporan oleh admin
    @POST("hapus-laporan/{id}")
    suspend fun deletelaporan(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body body: RequestBody
    ): LaporanResponse


    @GET("hapus-penyerahan/{id}")
    suspend fun deletePenyerahan(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): LaporanResponse

    @GET("laporan")
    suspend fun getListLaporan(
        @Header("Authorization") token: String,
    ): LaporanResponse


    //menampilkan laporan penyerahan
    @GET("get-penyerahan")
    suspend fun getListPenyerahan(
        @Header("Authorization") token: String,
    ): ResponsePenyerahan

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

    @GET("gethistory")
    suspend fun getHistoryDihapus(
        @Header("Authorization") token: String,
    ): NoPelaporanTeknisi

    //input sarana
    @POST("laporan")
    suspend fun insertLaporan(
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): LaporanResponse


    @POST("imageProfil")
    suspend fun insertFoto(
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): ProfileUserResponse

    @POST("kirimlaporanTeknisi")
    suspend fun insertLaporanTeknisi(
        @Header("Authorization") token: String,
        @Body body: KirimTeknisiRequest
    ): LaporanResponse


    //inputPenyerahan
    @POST("simpan-penyerahan")
    suspend fun inputPenyerahan(
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): PenyerahanResponse


    @GET("laporan-id/{id}")
    suspend fun getLaporan(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): LaporanIdResponse

    @GET("detail-penyerahan/{id}")
    suspend fun getPenyerahan(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): DetailPenyerahanResponse


    @POST("kirimlaporan")
    suspend fun kirimLaporanPerbaikan(
        @Header("Authorization") token: String,
        @Body body: RequestBody
    ): LaporanResponse


    @GET("getnamateknisi")
    suspend fun getTeknisiNama(
        @Header("Authorization") token: String,
    ): TeknisiReponse

    @GET("getkodepengaduanteknisi")
    suspend fun getkodepengaduanteknisi(
        @Header("Authorization") token: String,
    ): NoPelaporanTeknisi


    @POST("addUser")
    suspend fun addUser(
        @Header("Authorization") token: String,
        @Body request: AddUserRequest
    ): AddUserResponse

    @POST("ganti-password")
    suspend fun gantiPassword(
        @Body body: RequestBody
    ): LaporanResponse




}