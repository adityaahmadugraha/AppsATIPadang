package com.aditya.appsatipadang.user.data.remote.request

import com.google.gson.annotations.SerializedName

data class InputKamtibmasRequest(

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("waktu")
    val waktu: String? = null,

    @field:SerializedName("gambar")
    val gambar: String? = null

)
