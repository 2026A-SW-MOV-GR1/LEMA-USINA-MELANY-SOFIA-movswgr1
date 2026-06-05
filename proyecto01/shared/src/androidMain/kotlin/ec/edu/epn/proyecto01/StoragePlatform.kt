package ec.edu.epn.proyecto01

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings_datastore")

actual class StoragePlatform {
    private val context = ContextProvider.applicationContext

    actual suspend fun savePreference(key: String, value: String) {
        val sharedPref = context.getSharedPreferences("plain_prefs", Context.MODE_PRIVATE)
        sharedPref.edit().putString(key, value).apply()
    }

    actual suspend fun getPreference(key: String): String? {
        val sharedPref = context.getSharedPreferences("plain_prefs", Context.MODE_PRIVATE)
        return sharedPref.getString(key, null)
    }

    actual suspend fun saveDataStore(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[dataStoreKey] = value
        }
    }

    actual suspend fun getDataStore(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            preferences[dataStoreKey]
        }.first()
    }

    actual suspend fun saveEncrypted(key: String, value: String) {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val encryptedPrefs = EncryptedSharedPreferences.create(
            "secure_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        encryptedPrefs.edit().putString(key, value).apply()
    }

    actual suspend fun getEncrypted(key: String): String? {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val encryptedPrefs = EncryptedSharedPreferences.create(
            "secure_prefs",
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return encryptedPrefs.getString(key, null)
    }
}