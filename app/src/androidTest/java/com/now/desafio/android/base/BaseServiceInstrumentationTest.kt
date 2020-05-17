package com.now.desafio.android.base

import android.app.Activity
import android.content.Context
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

/** Classe base para simular requisições ao backend. */

@RunWith(AndroidJUnit4ClassRunner::class)
abstract class BaseServiceInstrumentationTest  {

    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
    protected lateinit var mockWebServer: MockWebServer


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

    protected fun isToastMessageDisplayed(activity: Activity, textId: Int) {
        Espresso.onView(ViewMatchers.withText(textId))
            .inRoot(RootMatchers.withDecorView(Matchers.not(Matchers.`is`(activity.window.decorView))))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    private fun readJson(file: String): String? =
            this::class.java.getResourceAsStream(file)?.readBytes()?.toString(Charsets.UTF_8)
}
