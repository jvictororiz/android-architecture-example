package com.now.desafio.android.usecase

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.now.desafio.android.base.BaseTest
import com.now.desafio.android.data.entities.Result
import com.now.desafio.android.data.entities.Artist
import com.now.desafio.android.service.ArtistRepository
import com.picpay.desafio.android.data.dao.entities.UserTable
import junit.framework.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock


class FindArtistsUseCaseImplTest : BaseTest() {

    @Mock
    private lateinit var artistRepository: ArtistRepository

    private lateinit var userUseCase: FindArtistUseCase


    @Before
    fun init() {
        userUseCase = FindArtistUseCaseImpl(artistRepository)
    }

    @Test
    fun `when FindArtistsUseCase calls listAllOnline with success then compare between expected and returned`() {
        val expectedArtists = listOf(Artist("img", "name", 0, false, "username"))
        var resultArtists: Result<List<Artist>?>? = null
        runBlocking {
            whenever(artistRepository.listAll()).thenReturn(Result.success(expectedArtists))
            resultArtists = userUseCase.listAllArtists(any())
        }
        assertNotNull(resultArtists)
        resultArtists?.let {
            assertTrue(it.isSuccessful())
            assertEquals(expectedArtists, it.data)
        }
    }

    @Test
    fun `when FindArtistsUseCase calls listAll with error then find offline`() {
        val expectedArtists = listOf(UserTable(0, "username", "img", "name"))
        var resultArtists: Result<List<Artist>?>? = null
        runBlocking {
            whenever(artistRepository.listAll()).thenReturn(Result.error("", 404))
            whenever(artistRepository.listAllCache()).thenReturn(expectedArtists)
            resultArtists = userUseCase.listAllArtists(any())
        }
        assertNotNull(resultArtists)
        resultArtists?.let {
            assertFalse(it.isSuccessful())
            assertTrue(it.isCacheSuccessful())
            assertNotNull(it.data)
        }
    }

    @Test
    fun `when FindArtistsUseCase calls listAll offline and has no data saved return error`()  {
        var resultArtists: Result<List<Artist>?>? = null
        runBlocking {
            whenever(artistRepository.listAll()).thenReturn(Result.error("", 404))
            whenever(artistRepository.listAllCache()).thenReturn(null)
            resultArtists = userUseCase.listAllArtists(any())
        }
        //Assert
        assertNotNull(resultArtists)
        resultArtists?.let {
            assertFalse(it.isSuccessful())
            assertFalse(it.isCacheSuccessful())
            assertNull(it.data)
        }
    }


}