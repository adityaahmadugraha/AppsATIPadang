package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class LaporanResponse(
    @field:SerializedName("status")
    val status: Int? = null,

    @field:SerializedName("laporan")
    val laporan: List<ItemLaporaneResponse?>? = null,
)