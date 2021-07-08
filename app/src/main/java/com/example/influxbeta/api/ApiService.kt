package com.example.influxbeta.api

import com.example.influxbeta.model.FoodData
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    /*
     companion object
  {
      //http://www.mocky.io/v2/5b700cff2e00005c009365cf
      const val BASE_URL:String = "https://www.mocky.io"
  }
     */
    @GET("/v2/5b700cff2e00005c009365cf")
    suspend fun getdata():Response<FoodData>
}