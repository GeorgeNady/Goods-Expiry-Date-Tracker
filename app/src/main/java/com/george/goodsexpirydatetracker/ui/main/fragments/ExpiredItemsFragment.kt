package com.george.goodsexpirydatetracker.ui.main.fragments

import com.george.goodsexpirydatetracker.base.fragment.ActivityFragmentAnnoation
import com.george.goodsexpirydatetracker.base.fragment.BaseFragment
import com.george.goodsexpirydatetracker.base.fragment.FragmentsLayouts.EXPIRED_ITEMS_FRAG
import com.george.goodsexpirydatetracker.databinding.FragmentExpiredItemBinding
import com.george.goodsexpirydatetracker.ui.main.MainActivity
import com.george.goodsexpirydatetracker.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ActivityFragmentAnnoation(EXPIRED_ITEMS_FRAG)
class ExpiredItemsFragment : BaseFragment<FragmentExpiredItemBinding>() {

    override val TAG: String get() = this.javaClass.name
    private val viewModel: MainViewModel by lazy { (activity as MainActivity).viewModel }

    override fun initialization() {}

    override fun setListener() {}
}