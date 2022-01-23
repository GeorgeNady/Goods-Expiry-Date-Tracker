package com.george.goodsexpirydatetracker.base

import android.content.BroadcastReceiver
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<B : ViewBinding>(
    val bindingFactory: (LayoutInflater) -> B
) : AppCompatActivity() {

    @Suppress("PropertyName")
    abstract val TAG: String
    val binding: B by lazy { bindingFactory(layoutInflater) }
    lateinit var broadcastReceiver: BroadcastReceiver
    private var mAlertDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        beforeCreatingView()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initialization()
        setListener()
    }

    abstract fun beforeCreatingView()
    abstract fun initialization()
    abstract fun setListener()

}