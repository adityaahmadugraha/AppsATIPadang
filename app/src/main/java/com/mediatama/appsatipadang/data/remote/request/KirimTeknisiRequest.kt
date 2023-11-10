package com.mediatama.appsatipadang.data.remote.request

import com.google.gson.annotations.SerializedName

data class KirimTeknisiRequest(
    @field:SerializedName("id_teknisi")
    val idTeknisi : String? = null,

    @field:SerializedName("id_laporan")
    val idLaporan : String? = null
)
