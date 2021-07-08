package com.example.influxbeta.repo

import com.example.influxbeta.api.ApiHelper

class MainRepo(private val apiHelper: ApiHelper) {
    suspend fun getData() = apiHelper.getData()

}
