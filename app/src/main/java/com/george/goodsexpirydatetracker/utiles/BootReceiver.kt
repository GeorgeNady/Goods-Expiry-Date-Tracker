package com.george.goodsexpirydatetracker.utiles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.george.goodsexpirydatetracker.services.AppForegroundService

class BootReceiver:BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
                val serviceIntent = Intent(context,AppForegroundService::class.java)
                context?.startForegroundService(serviceIntent)
            }
        }
    }
}