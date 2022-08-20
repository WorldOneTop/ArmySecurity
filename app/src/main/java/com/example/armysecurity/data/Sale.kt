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
data class Sale @ParcelConstructor constructor(
    @PrimaryKey
    @SerializedName("rowno")
    val rowno:String,
    @SerializedName("rgn")
    val rgn:String,
    @SerializedName("instltnnm")
    val instltnnm:String,
    @SerializedName("dcntenatvnm")
    val dcntenatvnm:String,
    @SerializedName("startday")
    val startday:String,
    @SerializedName("fnshday")
    val fnshday:String,
    @SerializedName("cntadr")
    val cntadr:String,
    @SerializedName("hmpg")
    val hmpg:String,
    @SerializedName("dtlexpln")
    val dtlexpln:String,
)

// rowno 행번호
// rgn 지역
// instltnnm 시설명
// dcntenatvnm 할인행사명
// startday 시작일
// fnshday 종료일
// cntadr 연락처
// hmpg 홈페이지 url 정상
// dtlexpln 상세설명
