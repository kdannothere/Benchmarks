package com.kdan.benchmarks.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kdan.benchmarks.repository.MapsRepository

class MapsViewModel : ViewModel() {

    val items = MutableLiveData<List<ItemData>>()
    val updater = MutableLiveData<Boolean>() // updates items in the adapter if value is true
    var collectionSize = 0
    val tagCollectionSize = "collectionSize"
    var elementsAmount = 0
    val tagElementsAmount = "elementsAmount"
    var buttonText = mutableListOf<String>()
    val repository = MapsRepository()
    private val _currentButtonText = MutableLiveData<String>()
    val currentButtonText: LiveData<String> // indexes: 0 current, 1 start, 2 stop
        get() = _currentButtonText


    fun setupItems() {
        if (items.value != null) return
        val itemDataList = mutableListOf<ItemData>()

        repeat(6) {
            val data = ItemData(id = it)
            itemDataList.add(data)
        }
        items.value = itemDataList
        setupListUpdater()
    }

    private fun setupListUpdater() {
        updater.value = false
    }

    fun start() {
        Thread.sleep(200)
        if (checkRange()) {
            if (repository.isRunning) {
                repository.currentOperation = -2
                return
            }
            changeButtonName(true)
            prepRep()
            repository.startAll()
        }
    }

    // prepare repository
    private fun prepRep() {
        repository.let {
            it.collectionSize = collectionSize
            it.elementsAmount = elementsAmount
            it.items = items
            it.updater = updater
        }
    }

    fun updateButtonText(newText: String) {
        _currentButtonText.value = newText
    }

    fun changeButtonName(stop: Boolean = false) {
        // indexes: 0 current, 1 start, 2 stop
        if (stop) buttonText[0] = buttonText[2] else buttonText[0] = buttonText[1]
        updateButtonText(buttonText.first())
    }

    // check collectionSize and elementsAmount
    private fun checkRange(): Boolean {
        val correctRange = 1000000..10000000
        if (collectionSize in correctRange &&
            elementsAmount in correctRange &&
            elementsAmount <= collectionSize
        ) return true
        return false
    }

}
