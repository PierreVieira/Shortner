package com.pierre.shortner.material_you.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import com.pierre.shortner.core.utils.orTrue
import com.pierre.shortner.material_you.domain.repository.MaterialYouRepository

internal class MaterialYouRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
) : MaterialYouRepository {

    override fun getIsDynamicColorsEnabledFlow(): Flow<Boolean> = dataStore.data.map {
        it[booleanPreferencesKey(IS_DYNAMIC_COLORS_ENABLED_KEY)].orTrue()
    }

    override suspend fun setIsDynamicColorsEnabled(isEnabled: Boolean) {
        dataStore.edit {
            it[booleanPreferencesKey(IS_DYNAMIC_COLORS_ENABLED_KEY)] = isEnabled
        }
    }

    companion object {
        private const val IS_DYNAMIC_COLORS_ENABLED_KEY = "is_dynamic_colors_enabled"
    }
}
