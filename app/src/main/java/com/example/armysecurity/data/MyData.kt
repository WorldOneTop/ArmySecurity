package com.example.armysecurity.data

import com.google.gson.annotations.SerializedName

data class MyData (
    @SerializedName("locate")
    val locate:String,
    @SerializedName("memo")
    var memo:String = "",
    @SerializedName("Fly")
    val fly:Fly? = null,
    @SerializedName("Relics")
    val relics:Relics? = null,
    @SerializedName("Sale")
    val sale:Sale? = null,
    @SerializedName("Trip")
    val trip:Trip? = null,
    @SerializedName("War")
    val war:War? = null,
    @SerializedName("WarMan")
    val warMan:WarMan? = null,
    @SerializedName("Cemetery")
    val cemetery:Cemetery? = null,
    @SerializedName("CemeteryDaejeon")
    val cemeteryDaejeon: CemeteryDaejeon? = null,
){
    fun isEqual(data:MyData):Boolean{
        val cacheMemo = memo
        memo = ""

        val result = this == data
        memo = cacheMemo

        return result
    }
}
data class MyDataList(
    @SerializedName("list")
    val list:ArrayList<MyData>,
)