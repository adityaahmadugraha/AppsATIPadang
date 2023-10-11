package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class TeknisiUserResponse(

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("user")
    val user: String

)
