package com.george.goodsexpirydatetracker.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.george.goodsexpirydatetracker.databinding.ItemCommodityBinding
import com.george.goodsexpirydatetracker.models.Commodity
import com.george.goodsexpirydatetracker.ui.main.fragments.ExpiredItemsFragment
import com.george.goodsexpirydatetracker.ui.main.fragments.HomeFragment
import com.george.goodsexpirydatetracker.utiles.DateHelper

class ExpiredItemsAdapter(
    val frag: ExpiredItemsFragment
) : ListAdapter<Commodity, ExpiredItemsAdapter.HomeViewHolder>(CommodityComparator) {

    inner class HomeViewHolder(val binding: ItemCommodityBinding) :
        RecyclerView.ViewHolder(binding.root)

    object CommodityComparator : DiffUtil.ItemCallback<Commodity>() {
        override fun areItemsTheSame(oldItem: Commodity, newItem: Commodity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Commodity, newItem: Commodity) = oldItem == newItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HomeViewHolder(
        ItemCommodityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.apply {
            tvName.text = item.name
            tvCategory.text = item.category

            DateHelper((item.expiryDate ?: 0).toLong()).also {
                tvExpDate.text = it.timestampConverter()
            }


        }

    }


}