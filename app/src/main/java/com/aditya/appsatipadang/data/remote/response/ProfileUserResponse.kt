package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileUserResponse(
    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("code")
    val code: Int,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("data")
    val userData: UserDataResponse
)
