package com.now.desafio.android

import com.now.desafio.android.service.ArtistServiceTest
import com.now.desafio.android.ui.viewModel.MainViewModelTest
import com.now.desafio.android.usecase.FindArtistsUseCaseImplTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainViewModelTest::class,
    FindArtistsUseCaseImplTest::class,
    ArtistServiceTest::class
)
class MainActivityTest