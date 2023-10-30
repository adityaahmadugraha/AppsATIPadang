package com.aditya.appsatipadang.data.remote.request

import com.google.gson.annotations.SerializedName

data class ResponseTest(
	@field:SerializedName("penyerahan")
	val penyerahan: List<PenyerahanItem>? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

