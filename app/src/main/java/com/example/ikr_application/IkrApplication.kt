package com.example.ikr_application

import android.app.Application
import timber.log.Timber

class IkrApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Timber logging
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
