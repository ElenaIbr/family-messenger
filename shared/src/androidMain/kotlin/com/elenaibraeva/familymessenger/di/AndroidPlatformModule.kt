package com.elenaibraeva.familymessenger.di

import android.content.Context
import com.elenaibraeva.familymessenger.core.network.NetworkConfig
import com.elenaibraeva.familymessenger.core.security.AndroidSecureStorage
import com.elenaibraeva.familymessenger.core.security.SecureStorage
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.android.Android
import org.koin.dsl.module

fun androidPlatformModule(context: Context) = module {

    single<HttpClientEngine> {
        Android.create()
    }

    single {
        NetworkConfig(
            baseUrl = "http://10.0.2.2:8080/"
        )
    }

    single<SecureStorage> {
        AndroidSecureStorage(context.applicationContext)
    }
}
