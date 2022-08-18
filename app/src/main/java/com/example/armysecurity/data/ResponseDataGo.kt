package com.example.armysecurity.data

import com.example.armysecurity.api.MndAPI
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel
import org.parceler.ParcelConstructor

data class ResponseDataGo(
    @SerializedName("body")
    val body:List<CemeteryDaejeon>,
    @SerializedName("header")
    val header:ResponseDataGoHeader
)

data class ResponseDataGoHeader(
    @SerializedName("resultMsg")
    val resultMsg:String,
    @SerializedName("totalRows")
    val totalRows:Int,
    @SerializedName("pageNo")
    val pageNo:String,
    @SerializedName("resultCode")
    val resultCode:String,
    @SerializedName("numOfRows")
    val numOfRows:String
)
