package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class InputLaporanResponse(
    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("message")
    val message: String? = null,
)
