package com.example.armysecurity.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Keep
@Parcel(Parcel.Serialization.BEAN)
@Entity
data class Relics @ParcelConstructor constructor(
    @PrimaryKey
    @SerializedName("rowno")
    val id:String,
    @SerializedName("ttl") //명칭
    val ttl:String,
    @SerializedName("natlthourperiod_1_ttl_1")//국적
    val natlthourperiod_1_ttl_1:String,
    @SerializedName("rgmn") //연대
    val rgmn:String,
    @SerializedName("obtmplace") //입수처
    val obtmplace:String,
    @SerializedName("relafr") //관련 사건
    val relafr:String,
    @SerializedName("chrtrst")// 특징
    val chrtrst:String,
    @SerializedName("wrtmyn") //전시여부
    val wrtmyn:String
)
