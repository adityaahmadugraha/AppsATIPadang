package com.aditya.appsatipadang.data.remote.response

import com.aditya.appsatipadang.data.local.UserLocal
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val status: Boolean,
    val message: String,
    val user: UserLocal?
)