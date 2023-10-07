package com.aditya.appsatipadang.data.remote.request

import com.google.gson.annotations.SerializedName

data class LaporanRequest(

    @field:SerializedName("id_teknisi")
    val id_teknisi: Int,

    @field:SerializedName("id_pelapor")
    val id_pelapor: Int,

    @field:SerializedName("type")
    val type: String,

    @field:SerializedName("jenis")
    val jenis: String,

    @field:SerializedName("tanggal")
    val tanggal: String,

    @field:SerializedName("waktu")
    val waktu: String,

    @field:SerializedName("lokasi")
    val lokasi: String,

    @field:SerializedName("merk")
    val merk: String,

    @field:SerializedName("biaya")
    val biaya: String,

    @field:SerializedName("status")
    val status: String,

    @field:SerializedName("deskripsi")
    val deskripsi: String,

    @field:SerializedName("foto")
    val foto: String,

    @field:SerializedName("foto_perbaikan")
    val foto_perbaikan: String,

    @field:SerializedName("kegiatan_perbaikan")
    val kegiatan_perbaikan: String,

    @field:SerializedName("pihak_terlibat")
    val pihak_terlibat: String,

    )