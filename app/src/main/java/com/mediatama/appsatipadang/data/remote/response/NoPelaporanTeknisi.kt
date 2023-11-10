package com.mediatama.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class NoPelaporanTeknisi(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("laporan")
	val laporan: List<LaporanItem?>? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class LaporanItem(

	@field:SerializedName("name_teknisi")
	val nameTeknisi: String? = null,

	@field:SerializedName("merk")
	val merk: String? = null,

	@field:SerializedName("pihak_terlibat")
	val pihakTerlibat: Any? = null,

	@field:SerializedName("id_teknisi")
	val idTeknisi: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("history")
	val history: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("alasan")
	val alasan: Any? = null,

	@field:SerializedName("name_pelapor")
	val namePelapor: String? = null,

	@field:SerializedName("kegiatan_perbaikan")
	val kegiatanPerbaikan: Any? = null,

	@field:SerializedName("biaya")
	val biaya: String? = null,

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("lokasi")
	val lokasi: String? = null,

	@field:SerializedName("jenis")
	val jenis: String? = null,

	@field:SerializedName("id_pelapor")
	val idPelapor: String? = null,

	@field:SerializedName("waktu")
	val waktu: String? = null,

	@field:SerializedName("foto_perbaikan")
	val fotoPerbaikan: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("tanggal")
	val tanggal: String? = null,

	@field:SerializedName("deskripsi")
	val deskripsi: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
