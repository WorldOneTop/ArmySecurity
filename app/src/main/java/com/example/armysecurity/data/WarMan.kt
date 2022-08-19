package com.example.armysecurity.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

// 625전쟁영웅
@Entity
data class WarMan (
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
)