package com.aditya.appsatipadang.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_data")

class UserDataManager(context: Context) {

    private val dataStore = context.dataStore

    suspend fun saveUser(userId: Int) {
        dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
        }
    }

    suspend fun getUserId(): Int {
        return dataStore.data.map { it[USER_ID_KEY] ?: 0 }.first()
    }

    companion object {
        private val USER_ID_KEY = intPreferencesKey("user_id")
    }
}