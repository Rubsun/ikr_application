package com.rin2396.impl.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.rin2396.api.domain.models.CatModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

private const val CATS_STORAGE = "cats_storage"
private const val TAG = "CatsLocalRepo"

internal class CatsLocalRepository(private val context: Context) {
    private val catsKey = stringSetPreferencesKey("cats")
    private val json = Json { ignoreUnknownKeys = true }

    // create DataStore explicitly to avoid relying on the preferencesDataStore delegate
    private val dataStore: DataStore<Preferences> by lazy {
        PreferenceDataStoreFactory.create {
            context.preferencesDataStoreFile(CATS_STORAGE)
        }
    }

    fun getCatsFlow(): Flow<List<CatModel>> {
        return dataStore.data.map { preferences ->
            val set = preferences[catsKey]
            val list = set?.mapNotNull { catJson ->
                try {
                    json.decodeFromString<CatModel>(catJson)
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to decode cat JSON: $catJson", e)
                    null
                }
            } ?: emptyList()

            Log.i(TAG, "getCatsFlow -> returning ${list.size} cats")
            list
        }
    }

    suspend fun addCat(cat: CatModel) {
        try {
            dataStore.edit { preferences ->
                val currentCats = (preferences[catsKey] ?: emptySet()).toMutableSet()
                val catJson = json.encodeToString(cat)
                currentCats.add(catJson)
                preferences[catsKey] = currentCats
                Log.i(TAG, "addCat -> saved cat id=${cat.id} size=${currentCats.size}")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save cat", e)
            throw e
        }
    }
}
