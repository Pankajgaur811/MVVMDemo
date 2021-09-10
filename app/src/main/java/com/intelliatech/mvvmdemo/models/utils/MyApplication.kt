package com.intelliatech.mvvmdemo.models.utils

import android.app.Application
import android.os.Build
import com.facebook.stetho.Stetho
import com.intelliatech.mvvmdemo.BuildConfig

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG)
            Stetho.initializeWithDefaults(this)
    }
}