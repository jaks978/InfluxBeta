package com.example.influxbeta.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable
data class FoodData (
    @SerializedName("Currency")
    val currency: String,
    @SerializedName("FoodList")
    val foodList: List<Food>,
    @SerializedName("status")
    val status: Status
)