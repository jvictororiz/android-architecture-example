package com.now.desafio.android.service

import com.now.desafio.android.base.BaseServiceTest
import com.now.desafio.android.data.service.NowService
import org.junit.Assert.*
import org.junit.Test


internal class ArtistServiceTest : BaseServiceTest() {

    @Test
    fun `Must list all users  successfully`() {
        val mockResponse = mockSuccessfulResponse("/json/response_users.json")
        mockWebServer.enqueue(mockResponse)
        val response = buildWebServiceTest<NowService>().getArtists().execute()
        assertNotNull(response.body())
        assertTrue(response.isSuccessful)
    }

    @Test
    fun `Must list all users  in error`() {
        val mockResponse = mockUnsuccessfulResponse()
        mockWebServer.enqueue(mockResponse)
        val response = buildWebServiceTest<NowService>().getArtists().execute()
        assertNull(response.body())
        assertFalse(response.isSuccessful)
    }
}
