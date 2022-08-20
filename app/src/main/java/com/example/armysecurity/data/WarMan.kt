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
data class WarMan @ParcelConstructor constructor(
    @SerializedName("title") // 이름
    val title:String,
    @SerializedName("ctnt") // 내용
    val content:String,
    @SerializedName("addtn_itm_5") // 생
    val date:String?,
    @SerializedName("addtn_itm_6") // 출생지
    val born:String?,
    @SerializedName("addtn_itm_7") // 계급
    val rank:String?,
    @SerializedName("addtn_itm_8") // 상훈 내역
    val award:String?,
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
){
    fun brToN() = content
        .replace("<br/>", "\n")
        .replace("&#39", "'")
}