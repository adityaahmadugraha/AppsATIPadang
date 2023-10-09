package com.aditya.appsatipadang.data.remote.request

import com.google.gson.annotations.SerializedName

data class StatusLaporan(


    @field:SerializedName("diterima")
    val sudah_di_terima_admin: String,


    @field:SerializedName("sedang_dikerjakan")
    val sedang_dikerjakan: String,


    @field:SerializedName("selesai")
    val selesai: String
)
