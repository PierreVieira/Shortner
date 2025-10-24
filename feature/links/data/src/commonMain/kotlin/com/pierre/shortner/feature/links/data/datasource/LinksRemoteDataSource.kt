package com.pierre.shortner.feature.links.data.datasource

import com.pierre.shortner.feature.links.data.dto.ShortenUrlDto

interface LinksRemoteDataSource {
    suspend fun postUrl(url: String): Result<ShortenUrlDto>
}
