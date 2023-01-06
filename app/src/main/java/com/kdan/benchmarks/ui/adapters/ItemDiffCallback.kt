package com.kdan.benchmarks.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import com.kdan.benchmarks.viewmodel.ItemData

object ItemDiffCallback : DiffUtil.ItemCallback<ItemData>() {
    override fun areItemsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ItemData, newItem: ItemData): Boolean {
        return when {
            oldItem.text != newItem.text -> false
            oldItem.visibilityOfBar != newItem.visibilityOfBar -> false
            else -> true
        }
    }
}
