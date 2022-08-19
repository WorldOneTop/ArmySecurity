package com.example.armysecurity.data

import com.google.gson.annotations.SerializedName

data class ResponseGithub (
    @SerializedName("relicsCount")
    val relicsCount:Int?,
    @SerializedName("data")
    val relics: List<Relics>?,
    @SerializedName("flyData")
    val fly: List<Fly>?
)