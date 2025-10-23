package com.pierre.shortner.model.data_status

/**
 * Represents the status of a data-loading operation.
 * Can either be [Loaded] containing actual data or [Loading] indicating data is still being fetched.
 */
sealed interface DataStatus<out T> {

    /**
     * Represents successfully loaded data.
     *
     * @param T The type of the data being wrapped.
     * @property data The loaded data.
     */
    data class Loaded<T>(
        val data: T,
    ) : DataStatus<T>

    /**
     * Represents a loading state while data is being fetched.
     */
    data object Loading : DataStatus<Nothing>
}
