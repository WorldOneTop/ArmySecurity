package com.example.armysecurity.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object GithubAPI {
    private const val BASE_URL = "https://raw.githubusercontent.com/WorldOneTop/ArmySecurityFile/main/"
    const val FILE_RELICS = "relics.json"
    const val FILE_FLY = "fly.json"
    const val FILE_VERSION = "version.json"

    val request: GithubRequest by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GithubRequest::class.java)
    }
}


interface GithubRequest {
    @GET("@{file}")
    suspend fun getData(@Path("file") file: String): String
}