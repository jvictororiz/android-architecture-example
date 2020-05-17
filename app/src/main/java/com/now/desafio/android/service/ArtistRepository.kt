package com.now.desafio.android.service

import com.now.desafio.android.data.entities.Artist
import com.now.desafio.android.data.entities.Result
import com.now.desafio.android.data.service.NowService
import com.now.desafio.android.util.ext.backgroundCall
import com.now.desafio.android.dao.UserDao
import com.picpay.desafio.android.data.dao.entities.UserTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArtistRepositoryImpl(
    private val nowService: NowService,
    private val userDao: UserDao
): ArtistRepository {
    override suspend fun listAll() = nowService.getUsers().backgroundCall(Dispatchers.IO)
    override suspend fun listAllCache() = withContext(Dispatchers.Main) { userDao.findAll() }
    override suspend fun saveUsers(artists: List<UserTable>) = withContext(Dispatchers.Main) { userDao.saveAll(artists) }
    override suspend fun updateCacheUser(artist: UserTable) = withContext(Dispatchers.IO){userDao.update(artist)}
    override suspend fun selectCacheByUsername(username: String) =  withContext(Dispatchers.IO){userDao.selectByUsername(username)}

}

interface ArtistRepository {
    suspend fun listAll(): Result<List<Artist>?>
    suspend fun listAllCache(): List<UserTable>?
    suspend fun saveUsers(artists: List<UserTable>)
    suspend fun updateCacheUser(artist: UserTable)
    suspend fun selectCacheByUsername(username:String): UserTable?
}

