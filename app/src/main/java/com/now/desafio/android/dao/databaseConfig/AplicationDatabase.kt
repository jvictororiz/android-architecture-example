package com.now.desafio.android.dao.databaseConfig

import androidx.room.*
import com.now.desafio.android.dao.databaseConfig.AplicationDatabase.Companion.VERSION
import com.now.desafio.android.dao.UserDao
import com.picpay.desafio.android.data.dao.entities.UserTable

@Database(
    entities = [
        UserTable::class
    ],
    version = VERSION
)
abstract class AplicationDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    companion object {
        const val NAME = "database-d"
        const val VERSION = 1
    }
}