package com.now.desafio.android.modules

import com.now.desafio.android.service.UserRepository
import com.now.desafio.android.ui.navigations.mainFlow.MainViewModel
import com.now.desafio.android.ui.navigations.registerFlow.RegisterAndLoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel {
        MainViewModel(findUserUseCase = get(), userUseCase = get())
    }
    viewModel {
        RegisterAndLoginViewModel(userRepository = get(), userRegisterUseCse = get())
    }

}