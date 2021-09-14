package com.intelliatech.mvvmdemo.models.prefrencesManager

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

class SharePreferenceManager(context: Context) {
    private val FIRST_TIME_LAUNCH = "False"
    private val appContext = context.applicationContext
    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun setFirstLaunchStatus(launch: Boolean) {
        preference.edit().putBoolean(FIRST_TIME_LAUNCH, launch).apply()
    }

    fun getFirstLaunchStatus(): Boolean {
        return preference.getBoolean(FIRST_TIME_LAUNCH, false)
    }
}