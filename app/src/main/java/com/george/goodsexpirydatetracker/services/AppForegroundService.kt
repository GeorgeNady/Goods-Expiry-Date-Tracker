package com.george.goodsexpirydatetracker.services

import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay

class AppForegroundService : LifecycleService() {

    companion object {
        const val TAG = "AppForegroundService"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        lifecycleScope.launchWhenCreated {
            while (true) {
                Log.i(TAG, "App Foreground Service is running ...")
                delay(2000)
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

}