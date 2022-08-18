package com.example.armysecurity.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.armysecurity.api.MndAPI
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Keep
@Parcel(Parcel.Serialization.BEAN)
@Entity
data class Cemetery @ParcelConstructor constructor(
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

data class Cemetery2(
    @SerializedName("buralpsn_nm")
    val name:String,
    @SerializedName("rank")
    val rank:String, // 계급
    @SerializedName("sclpst")
    val identity:String, // 신분
    @SerializedName("bural_date")
    val moveDate:String, // 안장 일자
    @SerializedName("bural_pstn")
    val movePlc:String, // 안장 위치
    @SerializedName("death_day")
    val deathDate:String, // 사망 일자
    @SerializedName("death_plc")
    val deathPlc:String, // 사망 장소
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
){
    fun getChemeter() = Cemetery(
        name,rank,identity,moveDate,movePlc,deathDate,deathPlc
    )
}

@Keep
@Parcel(Parcel.Serialization.BEAN)
data class CemeteryDaejeon  @ParcelConstructor constructor(
    @SerializedName("name")
    var name:String,
    @SerializedName("birthday") //생년월일
    var birthday:String?,
    @SerializedName("deathday") // 사망일
    var deathday:String?,
    @SerializedName("burrday") // 안장일
    var burrday:String?,
    @SerializedName("qualification") // 대상자 구분
    var qualification:String?,
    @SerializedName("sn") // 군번
    var sn:String?,
    @SerializedName("pname1") // 배우자 1
    var pname1:String?,
    @SerializedName("pname2")// 배우자 2
    var pname2:String?,
    @SerializedName("pname3")// 배우자 3
    var pname3:String?,
    @SerializedName("loctype")// 묘역 타입
    var loctype:String?,
    @SerializedName("locname") // 묘역 명
    var locname:String?,
    @SerializedName("block") // 묘역 위치
    var block:String?,
    @SerializedName("detaillocation") // 상세 위치
    var detaillocation:String?,
    @SerializedName("graveno") // 묘지번호
    var graveno:String?
)
