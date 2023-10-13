package com.aditya.appsatipadang.data.remote.response

import com.google.gson.annotations.SerializedName

data class TeknisiReponse(

    @field:SerializedName("teknisi")
    val teknisi: List<TeknisiItem?>? = null,

    @field:SerializedName("status")
    val status: Int? = null
) {
    data class TeknisiItem(

        @field:SerializedName("name")
        val name: String? = null
    )
}