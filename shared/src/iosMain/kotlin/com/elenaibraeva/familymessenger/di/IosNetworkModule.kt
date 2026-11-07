package com.elenaibraeva.familymessenger.di

import com.elenaibraeva.familymessenger.core.network.NetworkConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

val iosNetworkModule = module {
    single<HttpClientEngine> {
        Darwin.create()
    }

    single {
        NetworkConfig(
            baseUrl = "http://localhost:8080/"
        )
    }
}
