package com.pierre.shortner.feature.links.input.data.repository.datasource

import com.pierre.shortner.feature.links.input.data.dto.LinksDto
import com.pierre.shortner.feature.links.input.data.dto.ShortenUrlDto
import com.pierre.shortner.network.data.handler.RequestHandler
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.HttpRequestData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LinkInputRemoteDataSourceImplTest {

    @Test
    fun `given valid URL when postUrl then returns success with ShortenUrlDto`() = runTest {
        // Given
        val testUrl = "https://example.com"
        val expectedResponse = ShortenUrlDto(
            alias = "abc123",
            linksDto = LinksDto(
                original = testUrl,
                short = "https://short.ly/abc123"
            )
        )
        
        val engine = MockEngine { request ->
            verifyRequestDetails(request)
            respond(
                content = createSuccessResponse("abc123", testUrl, "https://short.ly/abc123"),
                status = HttpStatusCode.OK,
                headers = jsonHeaders
            )
        }
        
        val dataSource = createDataSource(engine)

        // When
        val result = dataSource.postUrl(testUrl)

        // Then
        assertTrue(result.isSuccess, "Expected success but was $result")
        assertEquals(expectedResponse, result.getOrNull())
    }

    @Test
    fun `given server error when postUrl then returns failure`() = runTest {
        // Given
        val testUrl = "https://example.com"
        
        val engine = MockEngine { _ ->
            respond(
                content = "Internal Server Error",
                status = HttpStatusCode.InternalServerError,
                headers = textHeaders
            )
        }
        
        val dataSource = createDataSource(engine)

        // When
        val result = dataSource.postUrl(testUrl)

        // Then
        assertTrue(result.isFailure, "Expected failure but was $result")
        val exception = result.exceptionOrNull()
        assertTrue(exception?.message?.contains("500") == true, "Expected 500 error but was: ${exception?.message}")
    }

    @Test
    fun `given bad request when postUrl then returns failure`() = runTest {
        // Given
        val testUrl = "invalid-url"
        
        val engine = MockEngine { _ ->
            respond(
                content = "Bad Request",
                status = HttpStatusCode.BadRequest,
                headers = textHeaders
            )
        }
        
        val dataSource = createDataSource(engine)

        // When
        val result = dataSource.postUrl(testUrl)

        // Then
        assertTrue(result.isFailure, "Expected failure but was $result")
        val exception = result.exceptionOrNull()
        assertTrue(exception?.message?.contains("400") == true, "Expected 400 error but was: ${exception?.message}")
    }

    @Test
    fun `given network exception when postUrl then returns failure`() = runTest {
        // Given
        val testUrl = "https://example.com"
        
        val engine = MockEngine { _ ->
            throw RuntimeException("Network error")
        }
        
        val dataSource = createDataSource(engine)

        // When
        val result = dataSource.postUrl(testUrl)

        // Then
        assertTrue(result.isFailure, "Expected failure but was $result")
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }

    @Test
    fun `given malformed JSON response when postUrl then returns failure`() = runTest {
        // Given
        val testUrl = "https://example.com"
        
        val engine = MockEngine { _ ->
            respond(
                content = """{"invalid": json}""",
                status = HttpStatusCode.OK,
                headers = jsonHeaders
            )
        }
        
        val dataSource = createDataSource(engine)

        // When
        val result = dataSource.postUrl(testUrl)

        // Then
        assertTrue(result.isFailure, "Expected failure but was $result")
        val exception = result.exceptionOrNull()
        assertTrue(exception?.message?.contains("json") == true || exception?.message?.contains("serialization") == true, 
            "Expected JSON parsing error but was: ${exception?.message}")
    }

    @Test
    fun `given empty URL when postUrl then makes request with empty body`() = runTest {
        // Given
        val testUrl = ""
        
        val engine = MockEngine { request ->
            verifyRequestDetails(request)
            respond(
                content = createSuccessResponse("empty", "", "https://short.ly/empty"),
                status = HttpStatusCode.OK,
                headers = jsonHeaders
            )
        }
        
        val dataSource = createDataSource(engine)

        // When
        val result = dataSource.postUrl(testUrl)

        // Then
        assertTrue(result.isSuccess, "Expected success but was $result")
        val response = result.getOrNull()
        assertEquals("", response?.linksDto?.original)
    }

    @Test
    fun `given very long URL when postUrl then makes request successfully`() = runTest {
        // Given
        val testUrl = "https://example.com/" + "a".repeat(1000)
        
        val engine = MockEngine { request ->
            verifyRequestDetails(request)
            respond(
                content = createSuccessResponse("long", testUrl, "https://short.ly/long"),
                status = HttpStatusCode.OK,
                headers = jsonHeaders
            )
        }
        
        val dataSource = createDataSource(engine)

        // When
        val result = dataSource.postUrl(testUrl)

        // Then
        assertTrue(result.isSuccess, "Expected success but was $result")
        val response = result.getOrNull()
        assertEquals(testUrl, response?.linksDto?.original)
    }

    @Test
    fun `given special characters in URL when postUrl then makes request successfully`() = runTest {
        // Given
        val testUrl = "https://example.com/path?param=value&other=test#fragment"
        
        val engine = MockEngine { request ->
            verifyRequestDetails(request)
            respond(
                content = createSuccessResponse("special", testUrl, "https://short.ly/special"),
                status = HttpStatusCode.OK,
                headers = jsonHeaders
            )
        }
        
        val dataSource = createDataSource(engine)

        // When
        val result = dataSource.postUrl(testUrl)

        // Then
        assertTrue(result.isSuccess, "Expected success but was $result")
        val response = result.getOrNull()
        assertEquals(testUrl, response?.linksDto?.original)
    }

    private fun createHttpClient(engine: MockEngine): HttpClient = HttpClient(engine) {
        install(ContentNegotiation) { json() }
        defaultRequest {
            url(BASE_URL)
            contentType(ContentType.Application.Json)
        }
    }

    private fun createDataSource(engine: MockEngine): LinkInputRemoteDataSourceImpl {
        val client = createHttpClient(engine)
        val requestHandler = RequestHandler(client)
        return LinkInputRemoteDataSourceImpl(requestHandler)
    }

    private fun createSuccessResponse(alias: String, originalUrl: String, shortUrl: String): String =
        """{"alias":"$alias","_links":{"self":"$originalUrl","short":"$shortUrl"}}"""

    private val jsonHeaders = headersOf(
        HttpHeaders.ContentType,
        ContentType.Application.Json.toString()
    )

    private val textHeaders = headersOf(
        HttpHeaders.ContentType,
        ContentType.Text.Plain.toString()
    )

    private fun verifyRequestDetails(request: HttpRequestData) {
        assertEquals(API_ENDPOINT, request.url.encodedPath)
        assertEquals("POST", request.method.value)
    }

    private companion object {
        private const val BASE_URL = "https://example.com"
        private const val API_ENDPOINT = "/api/alias"
    }
}
