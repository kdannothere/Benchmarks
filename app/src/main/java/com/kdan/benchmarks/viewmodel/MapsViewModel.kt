package com.kdan.benchmarks.viewmodel


import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kdan.benchmarks.R
import com.kdan.benchmarks.repository.MapsRepository

class MapsViewModel : ViewModel() {

    val items = MutableLiveData<List<ItemData>>()
    var collectionSize = 10000
    val tagCollectionSize = "collectionSize"
    var elementsAmount = 10000
    val tagElementsAmount = "elementsAmount"
    var buttonText = mutableListOf<String>()
    val repository = MapsRepository()
    private val _currentButtonText = MutableLiveData<String>()
    val currentButtonText: LiveData<String> // indexes: 0 current, 1 start, 2 stop
        get() = _currentButtonText

    fun setupItems() {
        if (items.value != null) return
        val itemDataList = mutableListOf<ItemData>()

        repeat (6) {
            val data = ItemData(id = it)
            itemDataList.add(data)
        }
        items.value = itemDataList
    }

    fun start() {
        // Log.d("SHOW", "text = " + items.value!!.last().text)
        //setElementsAmount()
        if (repository.stop) {
            repository.changeAllBars(true)
            changeButtonName()
            Thread.sleep(100)
            repository.stop = false
            return
        }
        if (checkRange()) {
            changeButtonName(true)
            prepRep()
            repository.changeAllBars()
            repository.startAll()
            changeButtonName()
        } else {
            repository.stop = false
            // Toast.makeText(Activity().applicationContext, R.string.check_range_elements_amount, Toast.LENGTH_SHORT).show()
        }
    }

    // prepare repository
    private fun prepRep() {
        repository.let {
            it.collectionSize = collectionSize
            it.elementsAmount = elementsAmount
            it.items = items
        }
    }

    fun updateButtonText(newText: String) {
        _currentButtonText.postValue(newText)
    }

    fun changeButtonName(stop: Boolean = false) {
        // indexes: 0 current, 1 start, 2 stop
        if (stop) buttonText[0] = buttonText[2] else buttonText[0] = buttonText[1]
        updateButtonText(buttonText.first())
    }

    private fun checkRange(): Boolean {
        val correctRange = 10000..10000000
        if (collectionSize in correctRange &&
            elementsAmount in correctRange &&
            elementsAmount <= collectionSize) return true
        return false
    }

}
