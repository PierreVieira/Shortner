package com.pierre.shortner.feature.links.input.data.repository.datasource

import com.pierre.shortner.feature.links.input.data.dto.ShortenUrlDto

fun interface LinkInputRemoteDataSource {
    suspend fun postUrl(url: String): Result<ShortenUrlDto>
}
