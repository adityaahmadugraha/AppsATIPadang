package com.aditya.appsatipadang.data.remote.request

import com.google.gson.annotations.SerializedName

data class DetailPenyerahanResponse(

	@field:SerializedName("penyerahan")
	val penyerahan: Penyerahan? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Penyerahan(

	@field:SerializedName("foto")
	val foto: String? = null,

	@field:SerializedName("nama_penerima")
	val namaPenerima: String? = null,

	@field:SerializedName("id_teknisi")
	val idTeknisi: String? = null,

	@field:SerializedName("tgl_diserahkan")
	val tglDiserahkan: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("no_pengaduan")
	val noPengaduan: String? = null
)
