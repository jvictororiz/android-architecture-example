package com.now.desafio.android

import com.now.desafio.android.data.dao.ArtistDaoTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ArtistDaoTest::class,
    MainActivityTest::class

)
class MainActivityInstrumentationTestFlow