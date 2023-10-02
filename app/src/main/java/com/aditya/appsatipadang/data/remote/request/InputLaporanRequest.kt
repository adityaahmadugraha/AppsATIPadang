package com.aditya.appsatipadang.data.remote.request

import com.google.gson.annotations.SerializedName
import java.util.Date

data class InputLaporanRequest(

//    @field:SerializedName("type")
//    val type: String? = null,
//
//    @field:SerializedName("merk")
//    val merk: String? = null,
//
//    @field:SerializedName("tanggal")
//    val tanggal: String? = null,
//
//    @field:SerializedName("lokasi")
//    val lokasi: String? = null,
//
//    @field:SerializedName("jenis_kerusakan")
//    val jenis_kerusakan: String? = null,
//
//    @field:SerializedName("deskripsi_kerusakan")
//    val deskripsi_kerusakan: String? = null,
//
//    @field:SerializedName("deskripsi")
//    val deskripsi: String? = null,
//
//    @field:SerializedName("foto")
//    val gambar: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

//    @field:SerializedName("waktu")
//    val waktu: String = null,

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("merk")
    val merk: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("gambar")
    val gambar: String? = null

//    @field:SerializedName("created_at")
//    val createdAt: String = null,
//
//    @field:SerializedName("updated_at")
//    val updatedAt: Date? = null

)