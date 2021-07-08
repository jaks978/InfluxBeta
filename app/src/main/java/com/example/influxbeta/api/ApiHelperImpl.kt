package com.example.influxbeta.api

import com.example.influxbeta.model.FoodData
import retrofit2.Response

class ApiHelperImpl(private val apiService: ApiService) : ApiHelper
{
    override suspend fun getData(): Response<FoodData> = apiService.getdata()

}