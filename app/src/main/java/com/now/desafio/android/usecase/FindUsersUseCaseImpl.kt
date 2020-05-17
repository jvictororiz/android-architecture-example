package com.now.desafio.android.usecase

import com.now.desafio.android.data.entities.Artist
import com.now.desafio.android.data.entities.Result
import com.now.desafio.android.data.entities.User
import com.now.desafio.android.service.ArtistRepository
import com.now.desafio.android.util.ext.toUser
import com.now.desafio.android.util.ext.toUserTable

class FindArtistUseCaseImpl(val artistRepository: ArtistRepository) : FindArtistUseCase {
    override suspend fun listAllArtists(user: User): Result<List<Artist>?> {
        val result = artistRepository.listAll()
        if (result.isSuccessful()) {
            result.data = result.data?.map { artist ->
                user.favorites?.forEach { it ->
                    if (artist.id == it.id) artist.favorited = it.favorited
                }
                artist
            }
            result.data?.let {
                artistRepository.saveUsers(it.map { user -> user.toUserTable() })
            }
        } else {
            val cacheResult = artistRepository.listAllCache()?.map { it.toUser() }
            if (!cacheResult.isNullOrEmpty()) {
                result.data = cacheResult
            }
        }
        return result
    }

}

interface FindArtistUseCase {
    suspend fun listAllArtists(user: User): Result<List<Artist>?>
}