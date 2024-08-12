package com.example.baseandroidmvvm

import android.app.Application
import com.example.baseandroidmvvm.di.repositoryModule
import com.example.baseandroidmvvm.di.utilModule
import com.example.baseandroidmvvm.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(repositoryModule, utilModule, viewModelModule)
        }
    }
}