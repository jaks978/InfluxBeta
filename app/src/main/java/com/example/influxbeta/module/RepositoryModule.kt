package com.example.influxbeta.module

import com.example.influxbeta.repo.MainRepo
import org.koin.dsl.module

val repoModule = module {
    single {
        MainRepo(get())
    }
}