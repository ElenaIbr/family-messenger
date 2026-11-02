package com.elenaibraeva.familymessenger.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

val androidNetworkModule = module {
    single<HttpClientEngine> {
        Android.create()
    }
}
