package com.elenaibraeva.familymessenger.di

import MessageApi
import com.elenaibraeva.familymessenger.core.network.HttpClientFactory
import com.elenaibraeva.familymessenger.core.network.NetworkConfig
import com.elenaibraeva.familymessenger.feature.user.data.remote.UserApi
import io.ktor.client.engine.HttpClientEngine
import org.koin.dsl.module

val networkModule = module {
    single {
        HttpClientFactory.create(
            engine = get<HttpClientEngine>(),
            baseUrl = get<NetworkConfig>().baseUrl,
            enableLogging = true
        )
    }

    single { MessageApi(client = get()) }

    single {
        UserApi(
            client = get(),
            accessTokenProvider = get(),
        )
    }
}
