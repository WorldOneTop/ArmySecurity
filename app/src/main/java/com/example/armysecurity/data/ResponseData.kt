package com.example.armysecurity.data

import com.example.armysecurity.api.MndAPI
import com.google.gson.annotations.SerializedName


data class ResponseData(
    @SerializedName("RESULT")
    val error:ResponseError?,
    @SerializedName(MndAPI.TYPE.CEMETERY_1)
    val CEMETERY_1:ResponseCemetery1,
    @SerializedName(MndAPI.TYPE.CEMETERY_2)
    val CEMETERY_2:ResponseCemetery2,
    @SerializedName(MndAPI.TYPE.SALE)
    val SALE:ResponseSale
)
data class ResponseError(
    @SerializedName("CODE")
    val code:String,
    @SerializedName("MESSAGE")
    val message:String
)

data class ResponseCemetery1(
    @SerializedName("list_total_count")
    val count:Int,
    @SerializedName("row")
    val row:List<Cemetery>,
)
data class ResponseCemetery2(
    @SerializedName("list_total_count")
    val count:Int,
    @SerializedName("row")
    val row:List<Cemetery2>,
)
data class ResponseSale(
    @SerializedName("list_total_count")
    val count:Int,
    @SerializedName("row")
    val row:List<Sale>,

)