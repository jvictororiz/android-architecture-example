package com.now.desafio.android.data.service

import com.now.desafio.android.data.entities.Artist
import retrofit2.Call
import retrofit2.http.GET


interface NowService {

    @GET("users")
    fun getUsers(): Call<List<Artist>>
}