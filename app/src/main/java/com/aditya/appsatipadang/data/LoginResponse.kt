package com.aditya.appsatipadang.data

data class LoginResponse(
    val status: Boolean,
    val message: String,
    val user: User?
)