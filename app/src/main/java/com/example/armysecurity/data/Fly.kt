package com.example.armysecurity.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import org.parceler.Parcel
import org.parceler.ParcelConstructor

@Keep
@Parcel(Parcel.Serialization.BEAN)
data class Fly @ParcelConstructor constructor(
    @SerializedName("enatvnm")
    val enatvnm:String,
    @SerializedName("dates")
    val dates:String,
    @SerializedName("plc")
    val plc:String,
    @SerializedName("glry_link_addr")
    val glry_link_addr:String,
)