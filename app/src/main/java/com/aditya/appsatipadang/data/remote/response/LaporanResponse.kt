package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class LaporanResponse(
    @field:SerializedName("status")
    val status: Boolean? = null,

//    @field:SerializedName("message")
//    val laporan: String? = null,

    @field:SerializedName("laporan")
    val laporan: List<ItemLaporaneResponse?>? = null,
//
//    @field:SerializedName("success")
//    val success: Boolean? = null,
)