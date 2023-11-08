package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName


data class PenyerahanResponse(

    @field:SerializedName("status")
    val status: Int? = null,
    @field:SerializedName("message")
    val message: String? = null,

    ) {
    data class Penyerahan(

        @field:SerializedName("no_pengaduan")
        val noPengaduan: String? = null,

        @field:SerializedName("no_pengaduan")
        val namaPenerima: String? = null,

        @field:SerializedName("tgl_diserahkan")
        val tglDserahkan: String? = null,

        @field:SerializedName("foto")
        val foto: String? = null,
    )
}

