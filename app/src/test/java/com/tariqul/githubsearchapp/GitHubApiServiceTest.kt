package com.tariqul.githubsearchapp

import com.tariqul.githubsearchapp.data.remote.GitHubService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(MockitoJUnitRunner::class)
class GitHubApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: GitHubService

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(GitHubService::class.java)
    }

    @Test
    fun `searchRepositories makes correct network request`() = runBlocking {
        val mockResponse = MockResponse()
        mockResponse.setBody("""
            {
                "items": [
                    { "name": "Repo1" },
                    { "name": "Repo2" }
                ]
            }
        """.trimIndent())
        mockWebServer.enqueue(mockResponse)

        val response = apiService.searchRepositories("android")
        val request = mockWebServer.takeRequest()

        assertEquals("/search/repositories?q=android", request.path)
        assertEquals("GET", request.method)
        assertEquals(2, response.body()?.items?.size)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}
