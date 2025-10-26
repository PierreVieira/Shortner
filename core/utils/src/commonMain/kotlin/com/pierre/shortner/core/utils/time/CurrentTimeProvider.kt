package com.pierre.shortner.core.utils.time

fun interface CurrentTimeProvider {
    fun getCurrentTimeStamp(): Long
}
