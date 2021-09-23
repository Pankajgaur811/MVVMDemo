package com.intelliatech.mvvmdemo

import android.app.Application
import com.intelliatech.mvvmdemo.dependenciesInjection.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

 class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {

            androidContext(this@MyApplication)
            koin.loadModules(
                listOf(
                    dBModule, incomeRepoModule, expenseRepoModule, incomeExpenseRepoModule,
                    paymentRepoModule, utilityHelper,incomeVModule
                )
            )
        }
        instance = this
        // No need to cancel this scope as it'll be torn down with the process
    }

    companion object {
        private var instance: MyApplication? = null


        public fun getAppInstance(): MyApplication? {
            return instance
        }

    }

    override fun onTerminate() {
        super.onTerminate()
        stopKoin()
    }
}