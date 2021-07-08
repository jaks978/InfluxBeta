package com.example.influxbeta.model


import com.google.gson.annotations.SerializedName

data class Fnblist(
    @SerializedName("Cinemaid")
    val cinemaid: String,
    @SerializedName("ImageUrl")
    val imageUrl: String,
    @SerializedName("ImgUrl")
    val imgUrl: String,
    @SerializedName("ItemPrice")
    val itemPrice: String,
    @SerializedName("Name")
    val name: String,
    @SerializedName("OtherExperience")
    val otherExperience: String,
    @SerializedName("PriceInCents")
    val priceInCents: String,
    @SerializedName("SevenStarExperience")
    val sevenStarExperience: String,
    @SerializedName("SubItemCount")
    val subItemCount: Int,
    @SerializedName("subitems")
    val subitems: List<Subitem>,
    @SerializedName("TabName")
    val tabName: String,
    @SerializedName("VegClass")
    val vegClass: String,
    @SerializedName("VistaFoodItemId")
    val vistaFoodItemId: String
)