package com.aditya.appsatipadang.user.data.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse (
    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("roles")
    val roles: String? = null,

    @field:SerializedName("password")
    val password: String? = null,


)