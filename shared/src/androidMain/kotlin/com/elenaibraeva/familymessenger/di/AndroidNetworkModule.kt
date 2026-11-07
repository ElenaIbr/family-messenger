package com.elenaibraeva.familymessenger.di

import com.elenaibraeva.familymessenger.core.network.NetworkConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

val androidNetworkModule = module {
    single<HttpClientEngine> {
        Android.create()
    }

    single {
        NetworkConfig(
            baseUrl = "http://10.0.2.2:8080/"
        )
    }
}
