package com.example.influxbeta.Application

import android.app.Application
import com.example.influxbeta.module.appModule
import com.example.influxbeta.module.repoModule
import com.example.influxbeta.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class Application : Application()
{
    companion object
    {
        //http://www.mocky.io/v2/5b700cff2e00005c009365cf
        const val BASE_URL:String = "https://www.mocky.io"
    }
    override fun onCreate()
    {
        super.onCreate()
        startKoin {
            androidContext(this@Application)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}