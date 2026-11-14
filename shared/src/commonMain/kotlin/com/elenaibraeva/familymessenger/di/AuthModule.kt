package com.elenaibraeva.familymessenger.di

import com.elenaibraeva.familymessenger.core.auth.SupabaseConfig
import com.elenaibraeva.familymessenger.feature.auth.data.repository.SupabaseAuthRepository
import com.elenaibraeva.familymessenger.feature.auth.domain.repository.AuthRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authModule = module {

    single {
        SupabaseConfig(
            url = "https://qvmjkrcmulqyqbryhvws.supabase.co",
            publishableKey = "sb_publishable_1eHaOTSHZAfhN_skrmm5yQ_im21iL6Z"
        )
    }

    single {
        val config = get<SupabaseConfig>()

        createSupabaseClient(
            supabaseUrl = config.url,
            supabaseKey = config.publishableKey
        ) {
            install(Auth) {
                autoLoadFromStorage = false
                autoSaveToStorage = false
            }
        }
    }

    singleOf(::SupabaseAuthRepository) {
        bind<AuthRepository>()
    }
}
