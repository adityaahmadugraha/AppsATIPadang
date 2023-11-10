package com.mediatama.appsatipadang.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.mediatama.appsatipadang.utils.Constant
import com.mediatama.appsatipadang.utils.Constant.PREF_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userStore by preferencesDataStore(PREF_NAME)

class UserPreference(context: Context) {
    private val userDataStore = context.userStore
    fun getUser(): Flow<UserLocal> {
        return userDataStore.data.map { preferences ->
            val userLocal = UserLocal(

                preferences[Constant.TAG_ID] ?: "",
                preferences[Constant.TAG_NAME] ?: "",
                preferences[Constant.TAG_USERNAME] ?: "",
                preferences[Constant.TAG_EMAIL] ?: "",
                preferences[Constant.TAG_NO_TLP] ?: "",
                preferences[Constant.TAG_PASSWORD] ?: "",
                preferences[Constant.TAG_ROLES] ?: "",
                preferences[Constant.TAG_ALAMAT] ?: "",
                preferences[Constant.TAG_FOTO] ?: "",
                preferences[Constant.TAG_TOKEN] ?: "",
                preferences[Constant.TAG_FCMTOKEN] ?: "",
                preferences[Constant.TAG_CONFIRMLOGIN] ?: "",

                )
            userLocal

        }
    }

    suspend fun saveUser(user: UserLocal) {
        userDataStore.edit { preferences ->
            preferences[Constant.TAG_ID] = user.id
            preferences[Constant.TAG_NAME] = user.name
            preferences[Constant.TAG_USERNAME] = user.username
            preferences[Constant.TAG_EMAIL] = user.email
            preferences[Constant.TAG_NO_TLP] = user.no_telp
            preferences[Constant.TAG_PASSWORD] = user.password
            preferences[Constant.TAG_ROLES] = user.roles
            preferences[Constant.TAG_ALAMAT] = user.alamat
            preferences[Constant.TAG_FOTO] = user.foto
            preferences[Constant.TAG_TOKEN] = user.token
            preferences[Constant.TAG_FCMTOKEN] = user.fcmtoken
            preferences[Constant.TAG_CONFIRMLOGIN] = user.confirmLogin ?: ""
        }
    }

    suspend fun deleteUser() {
        userDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}