package com.example.influxbeta.model


import com.google.gson.annotations.SerializedName

data class Status(
    @SerializedName("Description")
    val description: String,
    @SerializedName("Id")
    val id: String
)