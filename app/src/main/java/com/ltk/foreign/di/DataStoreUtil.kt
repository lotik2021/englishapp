package com.ltk.foreign.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import javax.inject.Inject

class DataStoreUtil @Inject constructor(context: Context) {

    val dataStore = context.dataStore

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
        val IS_DARK_MODE_KEY = booleanPreferencesKey("dark_mode")
        val WORD_ID = longPreferencesKey("word_id")
        val DICT_ID = longPreferencesKey("dict_id")
    }
}
