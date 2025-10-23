package com.pierre.shortner.feature.links.data.datasource

import com.pierre.shortner.feature.links.data.model.ShortenUrlRequest
import com.pierre.shortner.feature.links.data.model.ShortenUrlResponse
import com.pierre.shortner.network.data.handler.RequestHandler
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class LinksRemoteDataSourceImpl(
    private val requestHandler: RequestHandler,
) : LinksRemoteDataSource {

    override suspend fun shortenUrl(url: String): Result<ShortenUrlResponse> =
        requestHandler.call<ShortenUrlResponse> {
            post("/api/alias") {
                setBody(ShortenUrlRequest(url))
            }
        }
}
