package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class LaporanInfoResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("laporan")
	val laporan: Laporan? = null,

	@field:SerializedName("status")
	val status: Int? = null
) {

	data class Laporan(

		@field:SerializedName("merk")
		val merk: String? = null,

		@field:SerializedName("lokasi")
		val lokasi: String? = null,

		@field:SerializedName("waktu")
		val waktu: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("tanggal")
		val tanggal: String? = null,

		@field:SerializedName("deskripsi")
		val deskripsi: String? = null,

		@field:SerializedName("type")
		val type: String? = null,

		@field:SerializedName("gambar")
		val gambar: String? = null
	)
}

