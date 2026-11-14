package com.elenaibraeva.familymessenger

import android.app.Application
import com.elenaibraeva.familymessenger.di.androidPlatformModule
import com.elenaibraeva.familymessenger.di.initKoin

class FamilyMessengerApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin(
            androidPlatformModule(this)
        )
    }
}
