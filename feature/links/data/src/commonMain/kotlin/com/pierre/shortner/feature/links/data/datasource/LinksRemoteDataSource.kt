package com.pierre.shortner.feature.links.data.datasource

import com.pierre.shortner.feature.links.data.model.ShortenUrlResponse

interface LinksRemoteDataSource {
    suspend fun shortenUrl(url: String): Result<ShortenUrlResponse>
}