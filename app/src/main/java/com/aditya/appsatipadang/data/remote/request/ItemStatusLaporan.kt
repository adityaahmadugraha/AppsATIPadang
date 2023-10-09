package com.aditya.appsatipadang.data.remote.request

import com.google.gson.annotations.SerializedName

data class ItemStatusLaporan(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("status")
    val status: List<StatusLaporan>? = null,
)
