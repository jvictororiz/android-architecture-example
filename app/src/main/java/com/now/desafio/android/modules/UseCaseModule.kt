package com.now.desafio.android.modules

import com.now.desafio.android.usecase.FindArtistUseCase
import com.now.desafio.android.usecase.FindArtistUseCaseImpl
import com.now.desafio.android.usecase.UserRegisterUseCase
import com.now.desafio.android.usecase.UserRegisterUseCaseImpl
import org.koin.dsl.module

val userUseCase = module {
    factory<FindArtistUseCase> { FindArtistUseCaseImpl(artistRepository = get()) }
    factory<UserRegisterUseCase> { UserRegisterUseCaseImpl(userRepository = get()) }
}