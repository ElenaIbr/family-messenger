package com.elenaibraeva.familymessenger

import android.app.Application
import com.elenaibraeva.familymessenger.di.androidNetworkModule
import com.elenaibraeva.familymessenger.di.initKoin

class FamilyMessengerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin(androidNetworkModule)
    }
}
