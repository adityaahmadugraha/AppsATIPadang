package com.aditya.appsatipadang.data.remote.request

import com.google.gson.annotations.SerializedName

data class InputLaporanTeknisiRequest(

    @field:SerializedName("nama_pelapor")
    val nama_pelapor: String? = null,

    @field:SerializedName("no_pengaduan")
    val no_pengaduan: String? = null,

    @field:SerializedName("jenis")
    val jenis: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("tindak_lanjut")
    val kegiatan_perbaikan: String? = null,

    @field:SerializedName("tindak_lanjut")
    val tindak_lanjut: String? = null,

    @field:SerializedName("pihak_terlibat")
    val pihak_terlibat: String? = null,

    @field:SerializedName("biaya")
    val biaya: String? = null,

    @field:SerializedName("foto_perbaikan")
    val foto_perbaikan: String? = null
)
