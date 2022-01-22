package com.george.goodsexpirydatetracker.ui.main.fragments

import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.george.goodsexpirydatetracker.R
import com.george.goodsexpirydatetracker.adapters.HomeAdapter
import com.george.goodsexpirydatetracker.base.fragment.ActivityFragmentAnnoation
import com.george.goodsexpirydatetracker.base.fragment.BaseFragment
import com.george.goodsexpirydatetracker.base.fragment.FragmentsLayouts.HOME_FRAG
import com.george.goodsexpirydatetracker.databinding.FragmentHomeBinding
import com.george.goodsexpirydatetracker.ui.main.MainActivity
import com.george.goodsexpirydatetracker.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ActivityFragmentAnnoation(HOME_FRAG)
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override val TAG: String get() = this.javaClass.name
    private val viewModel: MainViewModel by lazy { (activity as MainActivity).viewModel }
    private val homeAdapter by lazy { HomeAdapter(this) }

    override fun initialization() {
        binding!!.apply {
            // initialization
            setupRecyclerView()
            viewModel.apply {
                getAllItemsFromRemoteRepositories()
                goodsRepo.observe(this@HomeFragment) { res ->
                    res.handler(
                        loading = { showProgressBar() },
                        error = { hideProgressBar() },
                        failed = { hideProgressBar() },
                    ) {
                        hideProgressBar()
                        res.data!!.goods.forEach { commodity ->
                            insertCommodity(commodity)
                        }
                        getAllGoodsDescending().observe(viewLifecycleOwner) { res ->
                            homeAdapter.submitList(res)
                        }
                    }
                }
            }
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
//                val extras = FragmentNavigatorExtras(fabBarCode to "avatar_image")
                findNavController().navigate(R.id.barCodeScannerFragment, null, null /*extras*/)
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////// helper functions
    private fun FragmentHomeBinding.showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun FragmentHomeBinding.hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    ///////////////////////////////////////////////////////////////////////////////////////////// RV
    private fun FragmentHomeBinding.setupRecyclerView() {
        with(rvScannedItems) {
            adapter = homeAdapter
        }
    }

}