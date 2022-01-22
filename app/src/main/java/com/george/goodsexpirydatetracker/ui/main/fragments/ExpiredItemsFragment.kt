package com.george.goodsexpirydatetracker.ui.main.fragments

import com.george.goodsexpirydatetracker.adapters.ExpiredItemsAdapter
import com.george.goodsexpirydatetracker.adapters.HomeAdapter
import com.george.goodsexpirydatetracker.base.fragment.ActivityFragmentAnnoation
import com.george.goodsexpirydatetracker.base.fragment.BaseFragment
import com.george.goodsexpirydatetracker.base.fragment.FragmentsLayouts.EXPIRED_ITEMS_FRAG
import com.george.goodsexpirydatetracker.databinding.FragmentExpiredItemBinding
import com.george.goodsexpirydatetracker.databinding.FragmentHomeBinding
import com.george.goodsexpirydatetracker.models.Commodity
import com.george.goodsexpirydatetracker.ui.main.MainActivity
import com.george.goodsexpirydatetracker.ui.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
@ActivityFragmentAnnoation(EXPIRED_ITEMS_FRAG)
class ExpiredItemsFragment : BaseFragment<FragmentExpiredItemBinding>() {

    override val TAG: String get() = this.javaClass.name
    private val viewModel: MainViewModel by lazy { (activity as MainActivity).viewModel }
    private val homeAdapter by lazy { ExpiredItemsAdapter(this) }

    override fun initialization() {
        binding!!.apply {
            setupRecyclerView()
            viewModel.apply {
                getAllGoodsAscending().observe(viewLifecycleOwner) { res ->

                    val notExpiredItems = mutableListOf<Commodity>()
                    val currentDate = Date().time

                    res.forEach { c ->
                        if ((c.expiryDate!! * 1000) < currentDate) {
                            notExpiredItems.add(c)
                        }
                    }

                    homeAdapter.submitList(notExpiredItems)
                }
            }
        }
    }

    override fun setListener() {}

    ///////////////////////////////////////////////////////////////////////////////////////////// RV
    private fun FragmentExpiredItemBinding.setupRecyclerView() {
        with(rvExpiredItems) {
            adapter = homeAdapter
        }
    }
}