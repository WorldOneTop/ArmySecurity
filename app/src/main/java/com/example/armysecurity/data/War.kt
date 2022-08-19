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
data class War @ParcelConstructor constructor(
    @SerializedName("title") // 전투 명
    val title:String,
    @SerializedName("ctnt") // 내용
    val content:String,
    @SerializedName("addtn_itm_2") // 시기
    val time:String?,
    @SerializedName("addtn_itm_3") // 장소
    val place:String?,
    @SerializedName("addtn_itm_4") // 지휘관
    val man:String?,
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
){
    fun brToN() = content.replace("<br/>", "\n")
}