package com.george.goodsexpirydatetracker.ui.main.fragments

import com.george.goodsexpirydatetracker.base.fragment.ActivityFragmentAnnoation
import com.george.goodsexpirydatetracker.base.fragment.BaseFragment
import com.george.goodsexpirydatetracker.base.fragment.FragmentsLayouts.HOME_FRAG
import com.george.goodsexpirydatetracker.databinding.FragmentHomeBinding

@ActivityFragmentAnnoation(HOME_FRAG)
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val TAG: String get() = this.javaClass.name

    override fun initialization() {}

    override fun setListener() {}

}