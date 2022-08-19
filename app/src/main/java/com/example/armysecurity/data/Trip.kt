package com.example.armysecurity.data

import com.google.gson.annotations.SerializedName

data class Trip (
    @SerializedName("prgm_nm")
    val name:String,
    @SerializedName("prgm_perid")
    val perid:String,
    @SerializedName("prgm_prps")
    val prps:String,
    @SerializedName("prgm_expln")
    val expln:String,
    @SerializedName("ptcptn_methd")
    val methd:String,
    @SerializedName("guidnce")
    val guidnce:String,
    @SerializedName("orginl_site_url")
    val url:String,
)

// prgm_nm 프로그램 명
// prgm_perid 프로그램 시기
// prgm_prps 프로그램 목적
// prgm_expln 프로그램 설명
// ptcptn_methd 참여 방법
// guidnce 안내
// orginl_site_url 원본 사이트 URL  http:__www.army.mil.kr_event_visit_program_02_01.jsp
