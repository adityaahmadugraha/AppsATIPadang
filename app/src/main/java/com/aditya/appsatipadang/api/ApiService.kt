package com.aditya.appsatipadang.api

import com.aditya.appsatipadang.data.LoginRequest
import com.aditya.appsatipadang.data.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

}