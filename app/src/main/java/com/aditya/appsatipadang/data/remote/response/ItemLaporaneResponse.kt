package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class ItemLaporaneResponse(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("id_pelapor")
    val id_pelapor: Int? = null,

    @field:SerializedName("id_teknisi")
    val id_teknisi: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("jenis")
    val jenis: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("waktu")
    val waktu: String? = null,

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("merk")
    val merk: String? = null,

    @field:SerializedName("biaya")
    val biaya: String? = null,


    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,


    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("foto_perbaikan")
    val foto_perbaikan: String? = null,

    @field:SerializedName("kegiatan_perbaikan")
    val kegiatan_perbaikan: String? = null,

    @field:SerializedName("pihak_terlibat")
    val pihak_terlibat: String? = null,

    @field:SerializedName("token")
    val token: String? = null,

    )
