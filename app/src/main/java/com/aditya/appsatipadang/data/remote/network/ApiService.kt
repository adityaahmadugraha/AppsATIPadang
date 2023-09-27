package com.aditya.appsatipadang.data.remote.network

import com.aditya.appsatipadang.data.remote.request.LoginRequest
import com.aditya.appsatipadang.data.remote.response.LoginResponse
import com.aditya.appsatipadang.data.remote.response.ProfileUserResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("profile/me")
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

}