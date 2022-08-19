package com.example.armysecurity.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class War (
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
)