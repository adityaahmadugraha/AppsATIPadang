package com.aditya.appsatipadang.data.remote.request

import com.google.gson.annotations.SerializedName
import java.util.Date

data class InputLaporanRequest(

//    @field:SerializedName("id_pelapor")
//    val id_pelapor: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("merk")
    val merk: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("gambar")
    val gambar: String? = null

)
