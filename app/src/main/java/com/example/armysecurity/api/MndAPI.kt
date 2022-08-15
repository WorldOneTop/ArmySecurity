package com.example.armysecurity.api

import android.content.Context
import android.util.Log
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.armysecurity.BuildConfig
import com.example.armysecurity.data.ResponseData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path



object MndAPI {
    private const val BASE_URL = "https://openapi.mnd.go.kr/${BuildConfig.MND_API_KEY}/json/"
    const val MAX_VALUE = 65535
    object TYPE{
        const val CEMETERY_1 = "DS_TB_MND_NTNLMMCMT_BURALPRSTS"
        const val CEMETERY_2 = "DS_SEOULMMCMT_GRV_BURALP"
    }

    val request: Request by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Request::class.java)
    }
}


interface Request {
    @GET("${MndAPI.TYPE.CEMETERY_1}/{startIndex}/{endIndex}")
    suspend fun getCemetery1(@Path("startIndex") startIndex: Int,@Path("endIndex") endIndex: Int): ResponseData

    @GET("${MndAPI.TYPE.CEMETERY_2}/{startIndex}/{endIndex}")
    suspend fun getCemetery2(@Path("startIndex") startIndex: Int,@Path("endIndex") endIndex: Int): ResponseData

}
