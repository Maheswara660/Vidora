package com.maheswara660.vidora.core.datastore.datasource

import androidx.datastore.core.DataStore
import com.maheswara660.vidora.core.common.Logger
import com.maheswara660.vidora.core.model.PlayerPreferences
import javax.inject.Inject

class PlayerPreferencesDataSource @Inject constructor(
    private val preferencesDataStore: DataStore<PlayerPreferences>,
) : PreferencesDataSource<PlayerPreferences> {

    companion object {
        private const val TAG = "PlayerPreferencesDataSource"
    }

    override val preferences = preferencesDataStore.data

    override suspend fun update(transform: suspend (PlayerPreferences) -> PlayerPreferences) {
        try {
            preferencesDataStore.updateData(transform)
        } catch (ioException: Exception) {
            Logger.logError(TAG, "Failed to update app preferences: $ioException")
        }
    }
}
