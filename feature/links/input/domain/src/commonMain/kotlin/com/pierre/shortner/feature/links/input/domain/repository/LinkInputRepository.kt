package com.pierre.shortner.feature.links.input.domain.repository

interface LinkInputRepository {
    suspend fun postUrl(url: String): Result<Unit>
    suspend fun getAllOriginalLinks(): List<String>
}
