package com.example.influxbeta.model


import com.google.gson.annotations.SerializedName

data class Subitem(
    @SerializedName("Name")
    val name: String,
    @SerializedName("PriceInCents")
    val priceInCents: String,
    @SerializedName("SubitemPrice")
    val subitemPrice: String,
    @SerializedName("VistaParentFoodItemId")
    val vistaParentFoodItemId: String,
    @SerializedName("VistaSubFoodItemId")
    val vistaSubFoodItemId: String
)