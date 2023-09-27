package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: UserResponse? = null,

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)
