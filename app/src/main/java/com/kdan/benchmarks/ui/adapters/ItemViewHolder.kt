package com.kdan.benchmarks.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.kdan.benchmarks.data.ItemData
import com.kdan.benchmarks.databinding.ItemViewBinding

class ItemViewHolder(
    private val binding: ItemViewBinding,
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var _itemData: ItemData

    fun bindData(itemData: ItemData) {
        _itemData = itemData
        binding.itemData = _itemData
        binding.executePendingBindings()
    }
}