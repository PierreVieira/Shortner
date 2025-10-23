package com.pierre.shortner.model.data_status

/**
 * Attempts to cast the current shimmer state to [DataStatus.Loaded] and returns it,
 * or returns null if the state is not loaded.
 *
 * @return The loaded state or null if not loaded.
 */
fun <T> DataStatus<T>.toLoadedInformation(): DataStatus.Loaded<T>? =
    this as? DataStatus.Loaded<T>

/**
 * Wraps the receiver value in a [DataStatus.Loaded] instance.
 *
 * @return A [DataStatus.Loaded] containing the current value.
 */
fun <T> T.toLoadedStatus(): DataStatus.Loaded<T> = DataStatus.Loaded(this)

/**
 * Transforms the loaded value of a [DataStatus] using the provided [transform] function with receiver.
 * If the current status is [DataStatus.Loaded], applies the transformation to its value and wraps the result
 * in a new [DataStatus.Loaded]. If the status is [DataStatus.Loading], it returns [DataStatus.Loading].
 *
 * This version uses a lambda with receiver, allowing for more concise syntax like `user.mapToStatus { name }`.
 *
 * Example usage:
 * ```
 * // Domain model
 * data class UserModel(
 *     val name: String,
 *     val photo: String?
 * )
 *
 * val userStatus: DataStatus<UserModel> = DataStatus.Loaded(
 *     UserModel(name = "Pierre", photo = "https://example.com/photo.jpg")
 * )
 *
 * val nameStatus: DataStatus<String> = userStatus.mapToStatus { name }
 * val photoStatus: DataStatus<String?> = userStatus.mapToStatus { photo }
 * ```
 *
 * @param transform A lambda with receiver that transforms the loaded value of type [T] into type [R].
 * @return A [DataStatus.Loaded] containing the transformed value, or [DataStatus.Loading] if the original
 *         status was loading.
 */
inline fun <T, R> DataStatus<T>.mapToStatus(
    transform: T.() -> R,
): DataStatus<R> = toLoadedData()?.let {
    DataStatus.Loaded(transform(it))
} ?: DataStatus.Loading

/**
 * Extracts the data from a shimmer state if it's [DataStatus.Loaded],
 * or returns null otherwise.
 *
 * @return The loaded data or null if not available.
 */
fun <T> DataStatus<T>.toLoadedData(): T? = toLoadedInformation()?.data

/**
 * Maps a list of shimmer states to a list of loaded data values,
 * excluding any items that are not in the loaded state.
 *
 * @return A list of data extracted from [DataStatus.Loaded] entries.
 */
fun <T> List<DataStatus<T>>.toLoadedData(): List<T> = mapNotNull { it.toLoadedData() }

/**
 * Converts a list of plain values to a list of [DataStatus.Loaded] instances.
 *
 * @return A list where each item is wrapped in [DataStatus.Loaded].
 */
fun <T> List<T>.toLoadedStatus(): List<DataStatus.Loaded<T>> = map { it.toLoadedStatus() }

/**
 * Checks whether the shimmer state represents a loading operation.
 *
 * @return `true` if the current state is [DataStatus.Loading], otherwise `false`.
 */
fun <T> DataStatus<T>.isLoading(): Boolean = this is DataStatus.Loading
