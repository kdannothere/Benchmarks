package com.kdan.benchmarks.viewmodel

import androidx.lifecycle.ViewModel
import com.kdan.benchmarks.repository.MapsRepository
import com.kdan.benchmarks.ui.CollectionSizeDialogFragment
import com.kdan.benchmarks.utility.Checker

class MapsViewModel : ViewModel() {

    var items = mutableListOf<ItemData>()
    var collectionSize = 0
    val tagCollectionSize = CollectionSizeDialogFragment().tagCollectionSize
    var elementsAmount = 0
    val tagElementsAmount = "elementsAmount"
    private val repository = MapsRepository()
    var buttonText = mutableListOf<String>()
    var temp = mutableSetOf<Int>()

    fun setupItems() {
        if (items.isNotEmpty()) return
        val itemDataList = mutableListOf<ItemData>()

        repeat(6) {
            val data = ItemData(id = it)
            itemDataList.add(data)
        }
        items = itemDataList
    }

    fun start() {
        if (repository.isRunning) {
            repository.currentOperation = -2
            return
        }
        if (repository.isDone) {
            Thread {
                    if (
                        Checker.checkCollectionSize(collectionSize) &&
                        Checker.checkElementsAmount(elementsAmount) &&
                        Checker.isGreaterOrEqual(collectionSize, elementsAmount)
                    ) {
                        changeButtonName(false)
                        prepRep()
                        repository.startAll()
                        changeButtonName(true)
                    }
            }.start()
            return
        }
        buttonText[0] = "Wait"
    }

    // prepare repository
    private fun prepRep() {
        repository.let {
            it.collectionSize = collectionSize
            it.elementsAmount = elementsAmount
            it.items = items
        }
    }

    private fun changeButtonName(stop: Boolean) {
        if (stop) buttonText[0] = buttonText[1] else buttonText[0] = buttonText[2]
    }

}
