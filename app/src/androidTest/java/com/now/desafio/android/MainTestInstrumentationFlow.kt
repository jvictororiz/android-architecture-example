package com.now.desafio.android

import com.now.desafio.android.data.dao.ArtistDaoTest
import com.now.desafio.android.ui.navigations.mainFlow.MainActivityTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ArtistDaoTest::class,
    MainActivityTest::class

)
class MainActivityInstrumentationTestFlow