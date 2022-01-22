package com.george.goodsexpirydatetracker.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.view.View
import androidx.activity.viewModels
import com.george.goodsexpirydatetracker.R
import com.george.goodsexpirydatetracker.base.BaseActivity
import com.george.goodsexpirydatetracker.databinding.ActivityMainBinding
import com.george.goodsexpirydatetracker.utiles.Constants.CHANNEL_DESCRIPTION
import com.george.goodsexpirydatetracker.utiles.Constants.CHANNEL_ID
import com.george.goodsexpirydatetracker.utiles.Constants.CHANNEL_NAME
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    override val TAG: String get() = this.javaClass.name
    val viewModel by viewModels<MainViewModel>()

    override fun beforeCreatingView() {
        setTheme(R.style.GoodsExpiryDateTrackerTheme)
    }

    override fun initialization() {
        createNotificationChannel()
    }

    override fun setListener() {
        // sendCommandToService(ACTION_START_OR_RESUME_SERVES)
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
                            viewModel.insertCommodity(res.data!!.goods[i])
                        }
                    }
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////// foreground service
    /*private fun sendCommandToService(action: String) {
        Intent(this, GoodsServes::class.java).also {
            it.action = action
            startService(it)
        }
    }*/

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