package com.pierre.shortner.core.utils


/**
 * Returns `true` if the given [index] corresponds to the last valid position
 * in this [List], or `false` otherwise.
 *
 * This is a convenience extension to check whether an index is the last element
 * without manually comparing against [List.lastIndex].
 *
 * Example usage:
 * ```
 * val list = listOf("a", "b", "c")
 * list.isLastIndex(2) // true
 * list.isLastIndex(1) // false
 * ```
 *
 * @param index The index to check.
 * @return `true` if [index] is equal to [lastIndex], `false` otherwise.
 */
fun <T> List<T>.isLastIndex(index: Int): Boolean = index == lastIndex
