package com.aditya.appsatipadang.repository

import com.aditya.appsatipadang.data.local.UserPreference
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteData: RemoteDataSource,
    private val localData: UserPreference
) {

}