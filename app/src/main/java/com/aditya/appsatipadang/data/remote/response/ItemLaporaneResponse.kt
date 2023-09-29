package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class ItemLaporaneResponse(
    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("waktu")
    val waktu: String? = null,

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("merk")
    val merk: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("foto")
    val foto: String? = null
)
