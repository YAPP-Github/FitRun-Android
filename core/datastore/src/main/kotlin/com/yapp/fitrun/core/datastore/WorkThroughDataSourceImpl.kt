package com.yapp.fitrun.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import com.yapp.fitrun.core.datastore.di.WorkThroughDataStore
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException
import javax.inject.Inject

class WorkThroughDataSourceImpl @Inject constructor(
    @WorkThroughDataStore val dataStore: DataStore<Preferences>,
) : WorkThroughDataSource {

    private companion object {
        private val KEY_IS_OPENED_FIRST_TIME = booleanPreferencesKey("is_opened_first_time")
    }

    override suspend fun setIsFirstTime(isFirstTime: Boolean) {
        dataStore.edit { preferences -> preferences[KEY_IS_OPENED_FIRST_TIME] = isFirstTime }
    }

    override suspend fun getIsFirstTime(): Boolean = dataStore.data
        .catch { exception ->
            if (exception is IOException) emit(emptyPreferences())
            else throw exception
        }.first()[KEY_IS_OPENED_FIRST_TIME] ?: true

}