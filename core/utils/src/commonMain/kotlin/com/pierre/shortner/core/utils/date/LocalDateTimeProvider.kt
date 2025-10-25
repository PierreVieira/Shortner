package com.pierre.shortner.core.utils.date

import kotlinx.datetime.LocalDateTime

fun interface LocalDateTimeProvider {
    fun getLocalDateTime(timestamp: Long): LocalDateTime
}
