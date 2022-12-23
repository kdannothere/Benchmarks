package com.kdan.benchmarks.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kdan.benchmarks.repository.MapsRepository
import com.kdan.benchmarks.ui.CollectionSizeDialogFragment

class MapsViewModel : ViewModel() {

    val items = MutableLiveData<List<ItemData>>()
    val updater = MutableLiveData<Boolean>() // updates items in the adapter if value is true
    var collectionSize = 30000
    val tagCollectionSize = CollectionSizeDialogFragment().tagCollectionSize
    var elementsAmount = 30000
    val tagElementsAmount = "elementsAmount"
    val repository = MapsRepository()
    var buttonText = mutableListOf<String>()


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
        if (checkRange()) {
            if (repository.isRunning) {
                repository.currentOperation = -2
                return
            }
            changeButtonName()
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

    fun changeButtonName(stop: Boolean = false) {
        if (stop) {
            buttonText[0] = buttonText[1] // text = start
        } else {
            buttonText[0] = buttonText[2] // text = stop
        }
    }

    private fun checkRange(): Boolean {
        val correctRange = 20000..10000000
        if (elementsAmount in correctRange &&
            elementsAmount <= collectionSize
        ) return true
        return false
    }

}
