package com.aditya.appsatipadang.data.remote.request

data class AddUserResponse(
	val message: String? = null,
	val user: User? = null,
	val status: Int? = null
)
data class User(
	val id: Int? = null
)

