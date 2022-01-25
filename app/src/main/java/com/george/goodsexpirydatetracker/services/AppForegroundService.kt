package com.george.goodsexpirydatetracker.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.george.goodsexpirydatetracker.R
import kotlinx.coroutines.delay

class AppForegroundService : LifecycleService() {

    companion object {
        const val TAG = "AppForegroundService"

        // foreground service dependencies
        const val NOTIFICATION_ID = 999
        const val NOTIFICATION_CHANNEL_ID = "app_foreground_notification_channel_id"
        const val NOTIFICATION_CHANNEL_NAME = "app_foreground_notification_channel_id"

    }

    private lateinit var channel: NotificationChannel
    private lateinit var notification: Notification.Builder

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        lifecycleScope.launchWhenCreated {
            while (true) {
                Log.i(TAG, "App Foreground Service is running ...")
                delay(2000)
            }
        }
        createNotificationChannel()
        createNotification()
        startForeground(NOTIFICATION_ID,notification.build())
        return super.onStartCommand(intent, flags, startId)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotification() {
        notification = Notification.Builder(this,NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_barcode_logo)
            .setContentTitle("service")
            .setContentText("this service is running to make some operation in the background")
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

}