package com.example.testapp

import android.app.Application
import com.example.testapp.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class TestApp : Application() {
    override fun onCreate() {
        super.onCreate()

//        if (BuildConfig.DEBUG) {
//            Timber.plant(Timber.DebugTree())
//        }

        startKoin {
            androidLogger()
            androidContext(this@TestApp)
            modules(
                listOf(
                    viewModelsModule
                )
            )
        }
    }
}