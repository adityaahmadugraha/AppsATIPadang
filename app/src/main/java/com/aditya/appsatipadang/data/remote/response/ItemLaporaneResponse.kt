package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class ItemLaporaneResponse(


    @field:SerializedName("merk")
    val merk: String? = null,

    @field:SerializedName("pihak_terlibat")
    val pihakTerlibat: String? = null,

    @field:SerializedName("id_teknisi")
    val idTeknisi: Int? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("jenis")
    val jenis: String? = null,

    @field:SerializedName("kegiatan_perbaikan")
    val kegiatanPerbaikan: String? = null,

    @field:SerializedName("biaya")
    val biaya: Int? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("lokasi")
    val lokasi: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id_pelapor")
    val idPelapor: Int? = null,

    @field:SerializedName("waktu")
    val waktu: String? = null,

    @field:SerializedName("foto_perbaikan")
    val fotoPerbaikan: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nama_pelapor")
    val namaPelapor: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("deskripsi")
    val deskripsi: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    )
