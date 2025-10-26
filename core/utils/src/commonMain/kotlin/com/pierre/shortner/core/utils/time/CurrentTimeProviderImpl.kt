package com.pierre.shortner.core.utils.time

import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CurrentTimeProviderImpl : CurrentTimeProvider {
    override fun getCurrentTimeStamp(): Long = Clock.System.now().toEpochMilliseconds()
}
