package com.pierre.shortener

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform