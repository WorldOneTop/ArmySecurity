package com.example.armysecurity.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.armysecurity.api.MndAPI
import com.google.gson.annotations.SerializedName

@Entity
data class Cemetery(
    @SerializedName("stmt")
    val name:String,
    @SerializedName("rank")
    val rank:String, // 계급
    @SerializedName("mildsc")
    val identity:String, // 신분
    @SerializedName("buraldate")
    val moveDate:String, // 안장 일자
    @SerializedName("buralpstn")
    val movePlc:String, // 안장 위치
    @SerializedName("dthdt")
    val deathDate:String, // 사망 일자
    @SerializedName("deathplc")
    val deathPlc:String, // 사망 장소
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
)

data class ResponseData(
    @SerializedName(MndAPI.TYPE.CEMETERY_1)
    val CEMETERY_1:ResponseCemetery,
    @SerializedName(MndAPI.TYPE.CEMETERY_2)
    val CEMETERY_2:ResponseCemetery
)
data class ResponseCemetery(
    @SerializedName("list_total_count")
    val count:Int,
    @SerializedName("row")
    val row:List<Cemetery>
)