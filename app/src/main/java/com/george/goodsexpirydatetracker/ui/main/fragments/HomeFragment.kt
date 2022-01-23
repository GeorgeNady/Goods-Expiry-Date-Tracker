package com.george.goodsexpirydatetracker.ui.main.fragments

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.george.goodsexpirydatetracker.R
import com.george.goodsexpirydatetracker.adapters.HomeAdapter
import com.george.goodsexpirydatetracker.base.fragment.ActivityFragmentAnnoation
import com.george.goodsexpirydatetracker.base.fragment.BaseFragment
import com.george.goodsexpirydatetracker.base.fragment.FragmentsLayouts.HOME_FRAG
import com.george.goodsexpirydatetracker.databinding.FragmentHomeBinding
import com.george.goodsexpirydatetracker.models.Commodity
import com.george.goodsexpirydatetracker.ui.main.MainActivity
import com.george.goodsexpirydatetracker.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
@ActivityFragmentAnnoation(HOME_FRAG)
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val TAG: String get() = this.javaClass.name
    private val viewModel: MainViewModel by lazy { (activity as MainActivity).viewModel }
    private val homeAdapter by lazy { HomeAdapter(this) }

    override fun initialization() {
        binding!!.apply {
            setupRecyclerView()
            viewModel.apply {
                getAllValidGoodsSortedByExpiryDateDescending().observe(viewLifecycleOwner) { res ->
                    homeAdapter.submitList(res)
                }
            }
        }
    }

    override fun setListener() {
        binding!!.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rvScannedItems.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                    if (scrollY > oldScrollY) {
                        fabBarCode.shrink()
                        hideToolBar()
                    }
                    else {
                        fabBarCode.extend()
                        showToolBar()
                    }
                }

            }

            fabBarCode.setOnClickListener {
                findNavController().navigate(R.id.barCodeScannerFragment, null, null /*extras*/)
            }

            toolbar.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.expiredItemsFragment -> {
                        findNavController().navigate(R.id.expiredItemsFragment)
                        true
                    }
                    R.id.settingFragment -> {
                        true
                    }
                }
                false
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////// helper functions
    private fun FragmentHomeBinding.showProgressBar() { progressBar.visibility = View.VISIBLE }

    private fun FragmentHomeBinding.hideProgressBar() { progressBar.visibility = View.GONE }

    private fun FragmentHomeBinding.showToolBar() {
        toolbar.animate().translationY(0F).setDuration(50).start()
    }

    private fun FragmentHomeBinding.hideToolBar() {
        val toolBarHeight = toolbar.height
        toolbar.animate().translationY((-toolBarHeight).toFloat()).setDuration(50).start()
    }

    ///////////////////////////////////////////////////////////////////////////////////////////// RV
    private fun FragmentHomeBinding.setupRecyclerView() {
        with(rvScannedItems) {
            adapter = homeAdapter
        }
    }

}