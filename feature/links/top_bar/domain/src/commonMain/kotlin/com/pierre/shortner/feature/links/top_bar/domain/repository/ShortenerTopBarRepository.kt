package com.pierre.shortner.feature.links.top_bar.domain.repository

interface ShortenerTopBarRepository {
    suspend fun getLinks(): List<Long>
}
