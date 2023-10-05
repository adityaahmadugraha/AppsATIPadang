package com.aditya.appsatipadang.user.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProfileUserResponse(
    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

)
