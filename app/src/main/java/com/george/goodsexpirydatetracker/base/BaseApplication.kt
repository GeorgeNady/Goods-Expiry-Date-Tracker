package com.george.goodsexpirydatetracker.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        lateinit var baseApplication: BaseApplication
    }

    override fun onCreate() {
        super.onCreate()
        baseApplication = this
    }

}