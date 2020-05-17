package com.now.desafio.android.data.dao

import android.content.Context
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.now.desafio.android.data.dao.databaseConfig.AplicationDatabase
import com.now.desafio.android.data.dao.entities.ArtistTable
import junit.framework.Assert.assertEquals
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ArtistDaoTest{


    private lateinit var db: AplicationDatabase
    private lateinit var dao: ArtistDao
    val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(context, AplicationDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        dao = db.userDao()
    }

    @After
    fun shutdown() = db.close()

    @Test
    fun testSaveAndFindArtistsData() {
        val usersExpected = listOf(ArtistTable(0, "username", "img","joao"))
        dao.saveAll(usersExpected)
        val usersRecovered = dao.findAll()
        assertEquals(usersExpected, usersRecovered)

    }
}