package com.aditya.appsatipadang.data.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserLocal(
    val id: Int?,
    val name: String,
    val username: String,
    val password: String,
    val roles: String,
    val created_at: String,
    val update_at: String
): Parcelable
