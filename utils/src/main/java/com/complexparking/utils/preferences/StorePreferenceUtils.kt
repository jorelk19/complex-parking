package com.complexparking.utils.preferences

import StorePreferencesSerializer
import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.byteArrayPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable

@Serializable
class StorePreferenceUtils(
    context: Context
) {
    private val Context.dataStore by preferencesDataStore(
        name = PREFERENCES_DATA_STORE_COMPLEX
    )

    private val dataStore = context.dataStore

    fun getString(key: String, defaultValue: String?): String? = runBlocking {
        return@runBlocking try {
            val preferencesKey = byteArrayPreferencesKey(key)

            val preference = dataStore.data.first()[preferencesKey]
            val decodedPreference = preference?.let {
                StorePreferencesSerializer.decryptData(preference)
            } ?: run { defaultValue }
            decodedPreference
        } catch (e: Exception) {
            defaultValue
        }
    }

    fun putString(key: String, value: String): Unit = runBlocking {
        dataStore.edit { preferences ->
            val encodedPreference = StorePreferencesSerializer.encryptData(value)
            preferences[byteArrayPreferencesKey(key)] = encodedPreference
        }
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean = runBlocking {
        return@runBlocking try {
            val preferencesKey = byteArrayPreferencesKey(key)
            val preference = dataStore.data.first()[preferencesKey]
            val decodedPreference = preference?.let {
                StorePreferencesSerializer.decryptData(preference).toBoolean()
            } ?: run { false }
            decodedPreference
        } catch (e: Exception) {
            Log.d("Exception: ", e.stackTrace.toString())
            defaultValue
        }
    }

    fun putBoolean(key: String, value: Boolean): Unit = runBlocking {
        dataStore.edit { preferences ->
            val encodedPreference = StorePreferencesSerializer.encryptData(value.toString())
            preferences[byteArrayPreferencesKey(key)] = encodedPreference
        }
    }

    fun remove(key: String): Unit = runBlocking {
        dataStore.edit {
            val preferenceKey = byteArrayPreferencesKey(key)
            it.remove(preferenceKey)
        }
    }

    companion object {
        private const val PREFERENCES_DATA_STORE_COMPLEX = "PREFERENCES_DATA_STORE_COMPLEX"
    }
}
