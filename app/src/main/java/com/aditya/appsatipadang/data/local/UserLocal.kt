package com.aditya.appsatipadang.data.local

data class UserLocal(
    val id: String,
    val name: String,
    val username: String,
    val email: String,
    val no_telp: String,
    val password: String,
    val roles: String,
    val alamat: String,
    val foto: String,
    val token: String,
    val fcmtoken: String,
    val confirmLogin: String? = null
)
