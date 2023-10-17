package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileUserResponse(
    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int,
)
