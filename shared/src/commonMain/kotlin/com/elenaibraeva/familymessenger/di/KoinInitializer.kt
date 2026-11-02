package com.elenaibraeva.familymessenger.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(platformModule: Module): KoinApplication = startKoin {
    modules(
        networkModule,
        platformModule
    )
}
