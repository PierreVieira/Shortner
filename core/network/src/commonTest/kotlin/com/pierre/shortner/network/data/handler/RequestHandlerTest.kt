package com.pierre.shortner.network.data.handler

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.Serializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RequestHandlerTest {

    @Serializable
    private data class TestDto(val value: Int)

    @Test
    fun `given 200 and json body when call then returns success with decoded body`() = runTest {
        // Given
        val engine = MockEngine { _ ->
            respond(
                content = """{"value":42}""",
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType,
                    ContentType.Application.Json.toString()
                ),
            )
        }
        val client = HttpClient(engine) {
            install(ContentNegotiation) { json() }
        }
        val handler = RequestHandler(client)

        // When
        val result = handler.call<TestDto> { get("https://example.com/success") }

        // Then
        assertTrue(result.isSuccess, "Expected success but was $result")
        assertEquals(TestDto(42), result.getOrNull())
    }

    @Test
    fun `given non-success status when call with onError then returns failure with mapped exception`() =
        runTest {
            // Given
            val engine = MockEngine { _ ->
                respond(
                    content = "Error",
                    status = HttpStatusCode.NotFound,
                    headers = headersOf(HttpHeaders.ContentType, ContentType.Text.Plain.toString()),
                )
            }
            val client = HttpClient(engine)
            val handler = RequestHandler(client)

            // When
            val result =
                handler.call<String>(onError = { code -> IllegalStateException("HTTP $code") }) {
                    // Any request, engine will respond 404
                    get("https://example.com/notfound")
                }

            // Then
            assertTrue(result.isFailure, "Expected failure but was $result")
            val message = result.exceptionOrNull()?.message
            assertEquals("HTTP 404", message)
        }

    @Test
    fun `given block throws exception when call then returns failure`() = runTest {
        // Given
        val client = HttpClient(MockEngine { _ ->
            respond(
                content = "irrelevant",
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, ContentType.Text.Plain.toString()),
            )
        })
        val handler = RequestHandler(client)

        // When
        val result = handler.call<String> {
            throw RuntimeException("boom")
        }

        // Then
        assertTrue(result.isFailure, "Expected failure but was $result")
        assertEquals("boom", result.exceptionOrNull()?.message)
    }
}
