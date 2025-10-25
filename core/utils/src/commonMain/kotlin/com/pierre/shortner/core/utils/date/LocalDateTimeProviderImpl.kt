package com.pierre.shortner.core.utils.date

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class LocalDateTimeProviderImpl : LocalDateTimeProvider {

    override fun getLocalDateTime(timestamp: Long): LocalDateTime = Instant
        .fromEpochMilliseconds(timestamp)
        .toLocalDateTime(TimeZone.currentSystemDefault())
}
