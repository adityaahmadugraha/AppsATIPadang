package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class LaporanResponse(
    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("id")
    val id : Int? = null,

    @field:SerializedName("laporan")
    val laporan: List<ItemLaporaneResponse>? = null,

    @field:SerializedName("message")
    val message: String? = null,
)