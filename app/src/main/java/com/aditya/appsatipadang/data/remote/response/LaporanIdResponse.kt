package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class LaporanIdResponse(

	@field:SerializedName("laporan")
	val laporan: Laporan? = null,

	@field:SerializedName("status")
	val status: Int? = null
) {
	data class Laporan(

		@field:SerializedName("merk")
		val merk: String? = null,

		@field:SerializedName("pihak_terlibat")
		val pihakTerlibat: Any? = null,

		@field:SerializedName("id_teknisi")
		val idTeknisi: Any? = null,

		@field:SerializedName("type")
		val type: String? = null,

		@field:SerializedName("kegiatan_perbaikan")
		val kegiatanPerbaikan: Any? = null,

		@field:SerializedName("biaya")
		val biaya: Int? = null,

		@field:SerializedName("foto")
		val foto: String? = null,

		@field:SerializedName("lokasi")
		val lokasi: String? = null,

		@field:SerializedName("id_pelapor")
		val idPelapor: Int? = null,

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
}


