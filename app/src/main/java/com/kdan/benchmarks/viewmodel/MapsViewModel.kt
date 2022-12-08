package com.kdan.benchmarks.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kdan.benchmarks.repository.MapsRepository
import com.kdan.benchmarks.ui.MapsInterface

class MapsViewModel : ViewModel(), MapsInterface {

    private val _items = MutableLiveData<List<ItemData>>()
    val items: LiveData<List<ItemData>>
        get() = _items
    // var countClicks = 0 // for stopping
    var stop = true
    var collectionSize = 1000000
    val tagCollectionSize = "collectionSize"
    var elementsAmount = 1000000
    val tagElementsAmount = "elementsAmount"
    var initialText = MutableList(6) { "0" }
    var buttonText = mutableListOf<String>()
    val calculation = MapsRepository
    private val _currentButtonText = MutableLiveData<List<String>>()
    val currentButtonText:LiveData<List<String>> // indexes: 0 current, 1 start, 2 stop
        get() = _currentButtonText


    // init { setupItems() }
    init {
        calculation.viewModel = this
    }

    fun setupItems() {
        val itemDataList = mutableListOf<ItemData>()

        for (count in 1..6) {
            val data = ItemData(id = count, text = "", visibilityOfBar = View.GONE)
            itemDataList.add(data)
        }
        //Activity().runOnUiThread {
        _items.value = itemDataList

    }

    fun updateButton(newText: MutableList<String>) {
    _currentButtonText.value = newText
    }


    fun changeText(index: Int, newText: String) {
        _items.value?.get(index)?.text = newText
    }

    fun changeResult(index: Int, newResult: Int) {
        _items.value?.get(index)?.result = newResult
    }

    fun changeBar(index: Int, stop: Boolean = false) {
        if (stop) {
            _items.value!![index].visibilityOfBar = View.GONE
        } else {
            _items.value!![index].visibilityOfBar = View.VISIBLE
        }
    }

    fun updateText(index: Int) {
        val result = _items.value?.get(index)?.result
        changeText(index, "${viewModel.initialText[index]} $result ms")
    }

    fun changeButtonName(stop: Boolean = false) {
        // indexes: 0 current, 1 start, 2 stop
        if (stop) buttonText[0] = buttonText[2] else buttonText[0] = buttonText[1]
        _currentButtonText.value = buttonText
    }

    fun changeAllBars(stop: Boolean = false) {
        for (index in 0..5) {
            changeBar(index, stop)
        }
    }

}