package com.pierre.shortner.network.data.handler

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

class RequestHandler(
    val httpClient: HttpClient,
) {
    suspend inline fun <reified T> call(
        noinline onError: ((errorCode: Int) -> Throwable?)? = null,
        crossinline block: suspend HttpClient.() -> HttpResponse,
    ): Result<T> = try {
        val response = httpClient.block()
        if (response.status.isSuccess()) {
            val body = response.body<T>()
            Result.success(body)
        } else {
            Result.failure(
                exception = onError
                    ?.invoke(response.status.value)
                    ?: Exception("Unexpected status code: ${response.status}"),
            )
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
