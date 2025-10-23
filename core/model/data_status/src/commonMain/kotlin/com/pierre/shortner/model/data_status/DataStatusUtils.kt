package com.pierre.shortner.model.data_status

/**
 * Utility class for working with lists of [DataStatus] objects.
 */
object DataStatusUtils {

    val loading = DataStatus.Loading

    /**
     * Generates a list containing the specified number of [DataStatus.Loading] items.
     *
     * If the [amount] is negative, an empty list is returned.
     *
     * @param amount The number of [DataStatus.Loading] items to generate.
     * @return A list of [DataStatus.Loading] of the given size, or an empty list if [amount] is negative.
     */
    fun generateLoading(amount: Int): List<DataStatus.Loading> = amount.takeUnless { it < 0 }?.let { safeAmount ->
        List(safeAmount) { DataStatus.Loading }
    }.orEmpty()
}
