package com.now.desafio.android.base

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/** Classe base para simular requisições ao backend. */
abstract class BaseServiceTest : BaseTest() {
    private val CONNECT_TIMEOUT: Long = 15L
    private val READ_TIMEOUT: Long = 15L

    protected lateinit var mockWebServer: MockWebServer
    protected val backend = createHttpClient()

    @Before
    fun init() {
        mockWebServer = MockWebServer().apply { start(8081) }
    }

    @After
    fun tearDown() = mockWebServer.shutdown()

    protected fun mockSuccessfulResponse(file: String): MockResponse = readJson(file)?.let {
        MockResponse()
            .setResponseCode(200)
            .setBody(it)
    }?:MockResponse()

    protected fun mockUnsuccessfulResponse(): MockResponse = readJson("/json/response_erro.json")?.let {
        MockResponse()
            .setResponseCode(500)
            .setBody(it)
    }?:MockResponse()

    protected inline fun <reified T> buildWebServiceTest(baseUrl: String ="http://localhost:8081/mbp-server/"): T {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(createHttpClient())
            .build()
        return retrofit.create(T::class.java)
    }

    fun createHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addNetworkInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        }.build()
    }

    private fun readJson(file: String): String? =
            this::class.java.getResourceAsStream(file)?.readBytes()?.toString(Charsets.UTF_8)

}
