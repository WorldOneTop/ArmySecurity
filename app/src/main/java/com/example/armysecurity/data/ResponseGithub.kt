package com.example.armysecurity.data

import com.google.gson.annotations.SerializedName

data class ResponseGithub (
    @SerializedName("relicsVersion")
    val relicsVersion:Int,
    @SerializedName("flyVersion")
    val flyVersion:Int,
    @SerializedName("data")
    val relics: List<Relics>,
    @SerializedName("flyData")
    val fly: List<Fly>
)