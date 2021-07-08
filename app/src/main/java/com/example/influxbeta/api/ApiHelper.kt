package com.example.influxbeta.api

import com.example.influxbeta.model.FoodData
import retrofit2.Response

interface ApiHelper {
    suspend fun getData(): Response<FoodData>
}
