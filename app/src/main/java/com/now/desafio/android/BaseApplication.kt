package com.now.desafio.android

import android.app.Application
import com.now.desafio.android.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

open class BaseApplication : Application() {
    open fun getApiUrl(): String {
        return BuildConfig.SERVER_URL
    }


    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            androidLogger()
            modules(
                listOf(
                    applicationModule,
                    repositoryModule,
                    databaseModules,
                    userUseCase,
                    viewModelModules
                )
            )
        }
    }
}