package com.aditya.appsatipadang.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.aditya.appsatipadang.di.Constant
import com.aditya.appsatipadang.di.Constant.PREF_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.userStore by preferencesDataStore(PREF_NAME)
class UserPreference(context: Context) {
    private val userDataStore = context.userStore
    fun getUser(): Flow<UserLocal> {
        return userDataStore.data.map { preferences ->
            UserLocal(
                preferences[Constant.KEY_ID] ?:hashCode(),
                preferences[Constant.KEY_NAME] ?: "",
                preferences[Constant.KEY_USERNAME] ?: "",
                preferences[Constant.KEY_PASSWORD] ?: "",
                preferences[Constant.KEY_ROLES] ?: "",
                preferences[Constant.KEY_CREATED_AT] ?: "",
                preferences[Constant.KEY_UPDATE_AT] ?: ""

            )

        }
    }

    suspend fun saveUser(user: UserLocal) {
        userDataStore.edit { preferences ->
            preferences[Constant.KEY_ID] = user.id as Int
            preferences[Constant.KEY_NAME] = user.name
            preferences[Constant.KEY_USERNAME] = user.username
            preferences[Constant.KEY_PASSWORD] = user.password
            preferences[Constant.KEY_ROLES] = user.roles
            preferences[Constant.KEY_CREATED_AT] = user.created_at
            preferences[Constant.KEY_UPDATE_AT] = user.update_at
        }
    }


    suspend fun deleteUser() {
        userDataStore.edit { preferences ->
            preferences.clear()
        }

    }
}