package com.george.goodsexpirydatetracker.ui.main

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.george.goodsexpirydatetracker.R
import com.george.goodsexpirydatetracker.base.BaseActivity
import com.george.goodsexpirydatetracker.databinding.ActivityMainBinding
import com.george.goodsexpirydatetracker.utiles.AlarmReceiver
import com.george.goodsexpirydatetracker.utiles.Constants.CHANNEL_DESCRIPTION
import com.george.goodsexpirydatetracker.utiles.Constants.CHANNEL_ID
import com.george.goodsexpirydatetracker.utiles.Constants.CHANNEL_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    override val TAG: String get() = this.javaClass.name
    val viewModel by viewModels<MainViewModel>()

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun beforeCreatingView() {
        setTheme(R.style.GoodsExpiryDateTrackerTheme)
    }

    override fun initialization() {
        createNotificationChannel()
    }

    override fun setListener() {
        binding.apply {
            viewModel.getAllItemsFromRemoteDataSource()
            viewModel.remoteDataSource.observe(this@MainActivity) { res ->
                res.handler(
                    loading = {showProgressBar()},
                    error = {hideProgressBar()},
                    failed = {hideProgressBar()},
                ) {
                    hideProgressBar()
                    runBlocking {
                        for (i in 0..3) {
                            val c = res.data!!.goods[i]
                            viewModel.insertCommodity(c)
                            val _6H = 21600000L
                            val _12H = 43200000L
                            val _18H = 64800000L
                            val _24H = 86400000L
                            setAlarmManager(c.expiryDate!!.toLong(),_6H)
                        }
                    }
                }
            }
        }
    }

    private fun setAlarmManager(timeInMillis:Long,interval:Long) {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this,AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,timeInMillis,interval,pendingIntent)
        Log.d(TAG, "setAlarmManager: alarm set")
    }

    /////////////////////////////////////////////////////////////////////////////////// notification
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance)
            channel.description = CHANNEL_DESCRIPTION

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    /////////////////////////////////////////////////////////////////////////////// helper functions
    private fun ActivityMainBinding.showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun ActivityMainBinding.hideProgressBar() {
        progressBar.visibility = View.GONE
    }


}