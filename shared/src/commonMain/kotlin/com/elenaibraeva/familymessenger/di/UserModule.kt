package com.elenaibraeva.familymessenger.di

import com.elenaibraeva.familymessenger.feature.user.data.repository.ProfileRepositoryImpl
import com.elenaibraeva.familymessenger.feature.user.domain.repository.ProfileRepository
import org.koin.dsl.module

val userModule = module {
    single<ProfileRepository> {
        ProfileRepositoryImpl(api = get())
    }
}
