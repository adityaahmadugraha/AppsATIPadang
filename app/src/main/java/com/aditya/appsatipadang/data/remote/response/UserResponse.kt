package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class UserResponse (
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("no_telp")
    val no_telp: String? = null,

    @field:SerializedName("roles")
    val roles: String? = null,

    @field:SerializedName("alamat")
    val alamat: String? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("fcmtoken")
    val fcmtoken : String? = null






)