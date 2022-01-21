package com.george.goodsexpirydatetracker.ui.main

import com.george.goodsexpirydatetracker.base.BaseActivity
import com.george.goodsexpirydatetracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    ActivityMainBinding::inflate
) {

    override val TAG: String get() = this.javaClass.name

    override fun beforeCreatingView() {}

    override fun initialization() {}

    override fun setListener() {}

}