package com.george.goodsexpirydatetracker.ui.main.fragments

import android.os.Build
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
                        loading = { showProgressDialog() },
                        error = { dismissProgressDialog() },
                        failed = { dismissProgressDialog() },
                    ) {
                        dismissProgressDialog()
                        goodsList.addAll(res.data!!.goods)
                        homeAdapter.submitList(goodsList)
                    }
                }
            }


            // viewModel.getAllGoodsDescending()
            // dataObserver()
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

    /*private fun dataObserver() {
        viewModel.goodsDescending.observe(viewLifecycleOwner) { res ->
            res.handler(
                loading = {
                    showProgressDialog()
                },
                error = {
                    dismissProgressDialog()
                },
                failed = {
                    dismissProgressDialog()
                },
            ) {
                dismissProgressDialog()
                homeAdapter.submitList(res.data)
                Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
            }
        }
    }*/

    ///////////////////////////////////////////////////////////////////////////////////////////// RV
    private fun FragmentHomeBinding.setupRecyclerView() {
        with(rvScannedItems) {
            adapter = homeAdapter
        }
    }

}