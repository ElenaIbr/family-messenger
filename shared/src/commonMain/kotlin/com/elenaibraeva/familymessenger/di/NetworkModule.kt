package com.elenaibraeva.familymessenger.di

import com.elenaibraeva.familymessenger.core.network.HttpClientFactory
import com.elenaibraeva.familymessenger.core.network.NetworkConfig
import io.ktor.client.engine.HttpClientEngine
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClientFactory.create(
            engine = get<HttpClientEngine>(),
            baseUrl = NetworkConfig.BASE_URL,
            enableLogging = true
        )
    }
}
