package com.pierre.shortner.core.data_store_provider

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

actual fun createDataStore(): DataStore<Preferences> = commonCreateDataStore { it }
