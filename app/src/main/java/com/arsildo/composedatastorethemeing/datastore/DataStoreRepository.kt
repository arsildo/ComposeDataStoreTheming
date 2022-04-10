package com.arsildo.composedatastorethemeing.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

class DataStoreRepository(context: Context) {
    private val Context.createDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private val themeSetting = intPreferencesKey("theme")
    private val dataStore = context.createDataStore

    suspend fun setApplicationTheme(id: Int) {
        dataStore.edit { settings ->
            settings[themeSetting] = id
        }
    }

    val currentTheme = dataStore.data.map { preferences ->
        preferences[themeSetting]
    }

}

enum class ApplicationThemes {
    Automatic,
    Light,
    Dark,
}