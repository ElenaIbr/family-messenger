package com.elenaibraeva.familymessenger.di

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

val iosNetworkModule = module {
    single<HttpClientEngine> {
        Darwin.create()
    }
}
