package com.elenaibraeva.familymessenger.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpStatusCode
import kotlin.test.Test
import kotlin.test.assertSame
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class NetworkModuleTest {

    @Test
    fun shouldProvideSingletonHttpClient() {
        val mockEngine = MockEngine {
            respond(
                content = "{}",
                status = HttpStatusCode.OK
            )
        }

        val koinApp = startKoin {
            modules(
                networkModule,
                module {
                    single<HttpClientEngine> { mockEngine }
                }
            )
        }

        try {
            val client1 = koinApp.koin.get<HttpClient>()
            val client2 = koinApp.koin.get<HttpClient>()

            assertSame(client1, client2)

            client1.close()
        } finally {
            stopKoin()
        }
    }
}
