package com.george.goodsexpirydatetracker.ui.main.fragments

import android.os.Build
import androidx.navigation.fragment.findNavController
import com.george.goodsexpirydatetracker.R
import com.george.goodsexpirydatetracker.adapters.HomeAdapter
import com.george.goodsexpirydatetracker.base.fragment.ActivityFragmentAnnoation
import com.george.goodsexpirydatetracker.base.fragment.BaseFragment
import com.george.goodsexpirydatetracker.base.fragment.FragmentsLayouts.HOME_FRAG
import com.george.goodsexpirydatetracker.databinding.FragmentHomeBinding
import com.george.goodsexpirydatetracker.ui.main.MainActivity
import com.george.goodsexpirydatetracker.ui.main.MainViewModel

@ActivityFragmentAnnoation(HOME_FRAG)
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val TAG: String get() = this.javaClass.name
    private lateinit var viewModel: MainViewModel
    private val homeAdapter by lazy { HomeAdapter(this) }

    override fun initialization() {
        viewModel = (activity as MainActivity).viewModel
        binding!!.apply {
            // init here
            setupRecyclerView()
            homeAdapter.submitList(viewModel.fake)
        }
    }

    override fun setListener() {
        binding!!.apply {
            // actions and events here
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rvScannedItems.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                    if (scrollY > oldScrollY) fabBarCode.shrink()
                    else fabBarCode.extend()
                }
            }

            fabBarCode.setOnClickListener {
                findNavController().navigate(R.id.barCodeScannerFragment, null, navOptions)
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////// RV
    private fun FragmentHomeBinding.setupRecyclerView() {
        with(rvScannedItems) {
            adapter = homeAdapter
        }
    }

}