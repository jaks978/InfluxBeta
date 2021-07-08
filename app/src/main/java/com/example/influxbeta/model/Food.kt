package com.example.influxbeta.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Food(
    @SerializedName("fnblist")
    val fnblist: List<Fnblist>,
    @SerializedName("TabName")
    val tabName: String
)