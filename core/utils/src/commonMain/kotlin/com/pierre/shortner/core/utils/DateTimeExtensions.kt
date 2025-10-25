package com.pierre.shortner.core.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.number

/**
 * Extension function to format LocalDateTime to dd/MM/yyyy HH:mm:ss format
 */
fun LocalDateTime.toFormattedString(connectorText: String): String = try {
    "${date.dayOfMonth.padded()}/${date.month.number.padded()}/${date.year} $connectorText ${time.toFormattedString()}"
} catch (_: Exception) {
    toString()
}

/**
 * Extension function to format LocalTime to HH:mm:ss format
 */
private fun LocalTime.toFormattedString(): String =
    "${hour.padded()}:${minute.padded()}:${second.padded()}"

/**
 * Extension function to pad integer with leading zero if needed
 */
private fun Int.padded(): String = toString().padStart(2, '0')
