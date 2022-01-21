package com.george.goodsexpirydatetracker.ui.main.fragments

import com.george.goodsexpirydatetracker.base.fragment.ActivityFragmentAnnoation
import com.george.goodsexpirydatetracker.base.fragment.BaseFragment
import com.george.goodsexpirydatetracker.base.fragment.FragmentsLayouts.BARCODE_FRAG
import com.george.goodsexpirydatetracker.databinding.FragmentBarCodeBinding

@ActivityFragmentAnnoation(BARCODE_FRAG)
class BarCodeScannerFragment : BaseFragment<FragmentBarCodeBinding>() {

    override val TAG: String get() = this.javaClass.name

    override fun initialization() {}

    override fun setListener() {}


}