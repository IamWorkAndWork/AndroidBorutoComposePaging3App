package com.example.myborutocomposepaging3app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.myborutocomposepaging3app.domain.repository.DataStoreOperations
import com.example.myborutocomposepaging3app.util.Constant.PREFERENCES_KEY
import com.example.myborutocomposepaging3app.util.Constant.PREFERENCES_NAME
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationsImpl(context: Context) : DataStoreOperations {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = PREFERENCES_KEY)
    }

    private val dataStore = context.dataStore

    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.onBoardingKey] = completed
        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data
            .catch { error ->
                if (error is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw error
                }
            }
            .map { preferences ->
                val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
                onBoardingState
            }
    }

}