package com.mediatama.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class DataUserResponse(

	@field:SerializedName("user")
	val user: User? = null,

	@field:SerializedName("status")
	val status: Int? = null
){
	data class User(

		@field:SerializedName("foto")
		val foto: Any? = null,

		@field:SerializedName("roles")
		val roles: String? = null,

		@field:SerializedName("name")
		val name: String? = null,

		@field:SerializedName("id")
		val id: Int? = null,

		@field:SerializedName("no_telp")
		val noTelp: String? = null,

		@field:SerializedName("email")
		val email: String? = null,

		@field:SerializedName("username")
		val username: String? = null,

		@field:SerializedName("alamat")
		val alamat: String? = null
	)
}


