package com.now.desafio.android.modules

import com.now.desafio.android.data.service.NowService
import com.now.desafio.android.data.service.UserFirebaseService
import com.now.desafio.android.data.service.UserFirebaseServiceImpl
import com.now.desafio.android.service.ArtistRepository
import com.now.desafio.android.service.ArtistRepositoryImpl
import com.now.desafio.android.service.UserRepository
import com.now.desafio.android.service.UserRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<ArtistRepository> { ArtistRepositoryImpl(userDao = get(), nowService = get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
}