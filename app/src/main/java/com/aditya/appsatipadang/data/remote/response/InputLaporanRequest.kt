package com.aditya.appsatipadang.data.remote.response

import java.sql.Time
import java.util.Date

data class InputLaporanRequest(
    val type: String,
    val tanggal: Date,
    val lokasi: String,
    val jenis_kerusakan: String,
    val deskripsi_kerusakan: String,
    val image: String

)
