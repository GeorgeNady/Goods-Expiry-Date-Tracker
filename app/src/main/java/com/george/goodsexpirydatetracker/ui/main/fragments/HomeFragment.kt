package com.george.goodsexpirydatetracker.ui.main.fragments

import android.os.Build
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
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
                getAllGoodsAscending().observe(viewLifecycleOwner) { res ->

                    val notExpiredItems = mutableListOf<Commodity>()
                    val currentDate = Date().time

                    res.forEach { c ->
                        if ((c.expiryDate!! * 1000) > currentDate) {
                            notExpiredItems.add(c)
                        }
                    }

                    homeAdapter.submitList(notExpiredItems)
                }
            }
        }
    }

    override fun setListener() {
        binding!!.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                rvScannedItems.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                    if (scrollY > oldScrollY) fabBarCode.shrink()
                    else fabBarCode.extend()
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

    ///////////////////////////////////////////////////////////////////////////////////////////// RV
    private fun FragmentHomeBinding.setupRecyclerView() {
        with(rvScannedItems) {
            adapter = homeAdapter
        }
    }

}