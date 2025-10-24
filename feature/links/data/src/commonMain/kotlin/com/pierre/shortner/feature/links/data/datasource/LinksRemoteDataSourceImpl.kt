package com.pierre.shortner.feature.links.data.datasource

import com.pierre.shortner.feature.links.data.dto.ShortenUrlRequest
import com.pierre.shortner.feature.links.data.dto.ShortenUrlDto
import com.pierre.shortner.network.data.handler.RequestHandler
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class LinksRemoteDataSourceImpl(
    private val requestHandler: RequestHandler,
) : LinksRemoteDataSource {

    override suspend fun postUrl(url: String): Result<ShortenUrlDto> =
        requestHandler.call<ShortenUrlDto> {
            post("/api/alias") {
                setBody(ShortenUrlRequest(url))
            }
        }
}
