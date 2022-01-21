package com.george.goodsexpirydatetracker.ui.main.fragments

import com.george.goodsexpirydatetracker.base.fragment.ActivityFragmentAnnoation
import com.george.goodsexpirydatetracker.base.fragment.BaseFragment
import com.george.goodsexpirydatetracker.base.fragment.FragmentsLayouts.EXPIRED_ITEMS_FRAG
import com.george.goodsexpirydatetracker.databinding.FragmentExpiredItemBinding

@ActivityFragmentAnnoation(EXPIRED_ITEMS_FRAG)
class ExpiredItemsFragment : BaseFragment<FragmentExpiredItemBinding>() {

    override val TAG: String get() = this.javaClass.name

    override fun initialization() {}

    override fun setListener() {}
}