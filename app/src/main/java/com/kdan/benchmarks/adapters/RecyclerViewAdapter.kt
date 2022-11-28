package com.kdan.benchmarks.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kdan.benchmarks.data.ItemData
import com.kdan.benchmarks.databinding.ItemViewBinding

class RecycleViewAdapter
    : ListAdapter<ItemData, ItemViewHolder> (ItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val itemBinding = ItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false)

        return ItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}