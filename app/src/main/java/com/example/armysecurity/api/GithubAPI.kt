package com.example.armysecurity.api

import com.example.armysecurity.data.ResponseGithub
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


object GithubAPI {
    private const val BASE_URL = "https://raw.githubusercontent.com/WorldOneTop/ArmySecurityFile/main/"
    const val FILE_RELICS = "relics"
    const val FILE_FLY = "fly"
    const val FILE_VERSION = "version"

    val request: GithubRequest by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().disableHtmlEscaping().create()))
            .build()
            .create(GithubRequest::class.java)
    }
}


interface GithubRequest {
    @GET("relics.json")
    suspend fun getRelics(): ResponseGithub
    @GET("fly.json")
    suspend fun getFly(): ResponseGithub
    @GET("version.json")
    suspend fun getVersion(): ResponseGithub
}