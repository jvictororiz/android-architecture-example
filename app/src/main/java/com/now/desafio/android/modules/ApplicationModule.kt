package com.now.desafio.android.modules

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.GsonBuilder
import com.now.desafio.android.BaseApplication
import com.now.desafio.android.data.service.NowService
import com.now.desafio.android.data.service.UserFirebaseService
import com.now.desafio.android.data.service.UserFirebaseServiceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private const val CONNECT_TIMEOUT: Long = 15L
private const val READ_TIMEOUT: Long = 15L

val applicationModule = module {
    single { buildWebService<NowService>((androidApplication() as BaseApplication).getApiUrl()) }
    single { FirebaseDatabase.getInstance() }
    single<UserFirebaseService> { UserFirebaseServiceImpl(get()) }
}

inline fun <reified T> buildWebService(baseUrl: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .client(createHttpClient())
        .build()
    return retrofit.create(T::class.java)
}

fun createHttpClient(): OkHttpClient {
    return OkHttpClient.Builder().apply {
        addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
    }.build()
}