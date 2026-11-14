package com.elenaibraeva.familymessenger.di

import com.elenaibraeva.familymessenger.core.auth.SupabaseConfig
import com.elenaibraeva.familymessenger.core.auth.SupabaseSessionManager
import com.elenaibraeva.familymessenger.feature.auth.data.repository.SupabaseAuthRepository
import com.elenaibraeva.familymessenger.feature.auth.domain.repository.AuthRepository
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.SessionManager
import io.github.jan.supabase.createSupabaseClient
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import com.elenaibraeva.familymessenger.AppViewModel
import com.elenaibraeva.familymessenger.feature.auth.presentation.login.EmailEntryViewModel
import com.elenaibraeva.familymessenger.feature.auth.presentation.verification.OtpVerificationViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf

val authModule = module {

    single {
        SupabaseConfig(
            url = "https://qvmjkrcmulqyqbryhvws.supabase.co",
            publishableKey = "sb_publishable_1eHaOTSHZAfhN_skrmm5yQ_im21iL6Z"
        )
    }

    single<SessionManager> {
        SupabaseSessionManager(
            secureStorage = get()
        )
    }

    single {
        val config = get<SupabaseConfig>()
        val secureSessionManager = get<SessionManager>()

        createSupabaseClient(
            supabaseUrl = config.url,
            supabaseKey = config.publishableKey
        ) {
            install(Auth) {
                sessionManager = secureSessionManager
                autoLoadFromStorage = true
                autoSaveToStorage = true
                alwaysAutoRefresh = true
            }
        }
    }

    singleOf(::SupabaseAuthRepository) {
        bind<AuthRepository>()
    }

    viewModelOf(::AppViewModel)
    viewModelOf(::EmailEntryViewModel)

    viewModel { (email: String) ->
        OtpVerificationViewModel(
            email = email,
            authRepository = get(),
        )
    }
}
