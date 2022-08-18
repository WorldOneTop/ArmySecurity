package com.example.armysecurity.api

import com.example.armysecurity.BuildConfig
import com.example.armysecurity.data.ResponseData
import com.example.armysecurity.data.ResponseDataGo
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

object DataAPI {
    private const val BASE_URL = "http://apis.data.go.kr/1180000/DaejeonNationalCemetery/"


    val request: DataRequest by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DataRequest::class.java)
    }
}


interface DataRequest {
    @GET("Burialinfo042")
    suspend fun getData(
        @Query("pageNo") page: Int,
        @Query("name") name: String,
        @Query("deathday") deathDay: String?, // 사망일
        @Query("qualification") qualification: String?, // 안장자격
        @Query("burrday") burrDay: String?,  //안장일
        @Query("sn") sn: String?, // 군번
        @Query("pname") pname: String?, // 배우자 이름
        @Query("graveno") graveNo: String?, // 묘지 번호
        @Query("serviceKey") key: String = BuildConfig.DATA_API_KEY,
        @Query("numOfRows") rows: Int = 50, // 묘지 번호
    ): ResponseDataGo

}
