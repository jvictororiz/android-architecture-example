package com.now.desafio.android.ui.viewModel

import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.now.desafio.android.base.BaseTest
import com.now.desafio.android.R
import com.now.desafio.android.data.entities.Result
import com.now.desafio.android.data.entities.Artist
import com.now.desafio.android.ui.navigations.mainFlow.MainViewModel
import com.now.desafio.android.usecase.FindArtistsUseCase
import junit.framework.Assert.assertNotNull
import junit.framework.Assert.assertNull
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock


class MainViewModelTest : BaseTest() {
    @Mock
    private lateinit var resultArtistsObserver: Observer<List<Artist>>
    @Mock
    private lateinit var alertOfflineObserver: Observer<Int>
    @Mock
    private lateinit var errorObserver: Observer<String>

    @Mock
    private lateinit var findArtistsUseCase: FindArtistsUseCase

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun start() {
        mainViewModel = MainViewModel(findArtistsUseCase)
    }


    @Test
    fun `when viewModel calls listAllArtists with success then sets resultArtists with success`() {
        mainViewModel = MainViewModel(findArtistsUseCase)
        runBlocking { whenever(findArtistsUseCase.listArtist()).thenReturn(Result.success(listOf())) }
        mainViewModel.resultArtistsObserver.observeForever(resultArtistsObserver)
        mainViewModel.findAllArtist()
        //Assert
        verify(resultArtistsObserver).onChanged(listOf())
        assertNotNull(mainViewModel.resultArtistsObserver.value)
    }

    @Test
    fun `when viewModel calls listAll with error then sets error LiveData`() {
        val expectedError = "Erro de conexão"
        runBlocking { whenever(findArtistsUseCase.listArtist()).thenReturn(Result.error(expectedError)) }
        mainViewModel.errorObserver.observeForever(errorObserver)
        mainViewModel.resultArtistsObserver.observeForever(resultArtistsObserver)
        mainViewModel.findAllArtist()
        //Assert
        verify(errorObserver).onChanged(expectedError)
        assertNull(mainViewModel.resultArtistsObserver.value)
    }

    @Test
    fun `when viewmodel calls listAll offline then return the datas in base with sucess`() {
        val expectedOfflineArtists = listOf(Artist("img", "joao", 1, "joaov"))
        runBlocking {
            whenever(findArtistsUseCase.listArtist()).thenReturn(Result.cacheSuccess(expectedOfflineArtists, mock()))
        }
        mainViewModel.resultArtistsObserver.observeForever(resultArtistsObserver)
        mainViewModel.alertOfflineObserver.observeForever(alertOfflineObserver)
        mainViewModel.errorObserver.observeForever(errorObserver)
        mainViewModel.findAllArtist()

        //Assert
        verify(alertOfflineObserver).onChanged(R.string.alert_offline)
        verify(resultArtistsObserver).onChanged(expectedOfflineArtists)
    }

    class TestFindCase  :FindArtistsUseCase{
        override suspend fun listArtist(): Result<List<Artist>?> {
            return Result.success(listOf())
        }

    }

}