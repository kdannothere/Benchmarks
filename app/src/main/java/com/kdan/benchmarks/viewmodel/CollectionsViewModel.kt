package com.kdan.benchmarks.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CollectionsViewModel : ViewModel() {
    private val _items = MutableLiveData<List<ItemData>>()
    val items: LiveData<List<ItemData>>
        get() = _items

    init {
        mockItems()
    }

    private fun mockItems() {
        val itemDataList = mutableListOf<ItemData>()

        for (count in 1..21) {
            val data =
                ItemData(id = count, text = "Text for id $count", visibilityOfBar = View.GONE)
            itemDataList.add(data)
        }
        _items.postValue(itemDataList)
    }

    fun changeText(index: Int, newText: String) {
        _items.value?.get(index)?.text = newText
    }

    fun changeResult(index: Int, newResult: Int) {
        _items.value!![index].result = newResult
    }

    fun changeBar(index: Int, stop: Boolean = false) {
        if (stop) {
            _items.value!![index].visibilityOfBar = View.GONE
        } else {
            val currentValue = _items.value!![index].visibilityOfBar
            if (currentValue == View.VISIBLE) {
                _items.value!![index].visibilityOfBar = View.GONE
            } else {
                _items.value!![index].visibilityOfBar = View.VISIBLE
            }
        }
    }
}