package com.george.goodsexpirydatetracker.utiles

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.DEFAULT_ALL
import androidx.core.app.NotificationCompat.PRIORITY_HIGH
import androidx.core.app.NotificationManagerCompat
import com.george.goodsexpirydatetracker.R
import com.george.goodsexpirydatetracker.base.BaseApplication.Companion.baseApplication
import com.george.goodsexpirydatetracker.ui.main.MainActivity
import com.george.goodsexpirydatetracker.utiles.Constants.CHANNEL_ID
import com.george.goodsexpirydatetracker.utiles.Constants.DESCRIPTION
import com.george.goodsexpirydatetracker.utiles.Constants.NOTIFICATION_ID
import com.george.goodsexpirydatetracker.utiles.Constants.TITLE

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(ctx: Context?, intnet: Intent?) {

        val mIntent = Intent(ctx, MainActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivities(ctx, 0, arrayOf(mIntent), 0)

        val notification = NotificationCompat.Builder(ctx ?: baseApplication, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_barcode_logo)
            .setContentTitle(TITLE)
            .setContentText(DESCRIPTION)
            .setAutoCancel(true)
            .setDefaults(DEFAULT_ALL)
            .setPriority(PRIORITY_HIGH)
            .setContentIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(ctx ?: baseApplication)
        notificationManager.notify(NOTIFICATION_ID, notification.build())

    }

}