package com.pierre.shortner.feature.links.input.data.repository.datasource

import com.pierre.shortner.feature.links.input.data.dto.ShortenUrlDto
import com.pierre.shortner.feature.links.input.data.dto.ShortenUrlRequest
import com.pierre.shortner.network.data.handler.RequestHandler
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class LinkInputRemoteDataSourceImpl(
    private val requestHandler: RequestHandler,
) : LinkInputRemoteDataSource {

    override suspend fun postUrl(url: String): Result<ShortenUrlDto> =
        requestHandler.call<ShortenUrlDto> {
            post("/api/alias") {
                setBody(ShortenUrlRequest(url))
            }
        }
}
