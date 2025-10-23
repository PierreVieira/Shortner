package com.pierre.shortner.core.utils

/**
 * Converts this [String] into **UpperCamelCase** (also known as Title Case),
 * where each word is capitalized and separated by a space.
 *
 * Words are split by spaces, trimmed, lowercased, and then the first character
 * of each word is converted to title case. Consecutive spaces and blank parts
 * are ignored in the result.
 *
 * Example usage:
 * ```
 * val input1 = "spider man"
 * val input2 = "GAME of thrones"
 *
 * println(input1.toUpperCamelCase()) // "Spider Man"
 * println(input2.toUpperCamelCase()) // "Game Of Thrones"
 * ```
 *
 * @receiver The original [String] to transform.
 * @return A new [String] with each word capitalized and formatted in UpperCamelCase.
 */
fun String.toUpperCamelCase(): String =
    split(" ")
        .filter { it.isNotBlank() }
        .joinToString(" ") { word ->
            word.lowercase()
                .replaceFirstChar { ch ->
                    if (ch.isLowerCase()) ch.titlecase() else ch.toString()
                }
        }

/**
 * Tries to extract a 4-digit year from an ISO-like date string (e.g., "2016-07-06").
 * Falls back to the first 4 chars if they form an Int, otherwise returns null.
 */
fun String?.safeYearOrNull(): Int? {
    if (this.isNullOrBlank()) return null
    // Fast path: "YYYY-MM-DD"
    this.takeIf { it.length >= 4 }?.substring(0, 4)?.toIntOrNull()?.let { return it }

    // Fallback: find a 4-digit year anywhere
    val match = Regex("""\b(19|20)\d{2}\b""").find(this) ?: return null
    return match.value.toIntOrNull()
}
