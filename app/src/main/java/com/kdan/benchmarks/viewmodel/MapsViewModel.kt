package com.kdan.benchmarks.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kdan.benchmarks.repository.MapsRepository
import com.kdan.benchmarks.ui.CollectionSizeDialogFragment
import com.kdan.benchmarks.utility.Checker
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MapsViewModel : ViewModel() {

    private val service: ExecutorService = Executors.newSingleThreadExecutor()
    private val repository = MapsRepository()
    var items = mutableListOf<ItemData>()
    var collectionSize = 10000 // test
    val tagCollectionSize = CollectionSizeDialogFragment.tagCollectionSize
    var elementsAmount = 10000 // test
    val tagElementsAmount = "elementsAmount"
    val buttonTextList = mutableListOf<String>()
    val buttonText = MutableLiveData<String>()
    val temp = mutableSetOf<Int>()
    var runnable: Runnable? = null
    val delay = 1000

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
            service.execute(
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
                }
            )
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

    private fun changeButtonName(stop: Boolean) {
        when (stop) {
            true -> buttonText.postValue(buttonTextList.first()) // start
            false -> buttonText.postValue(buttonTextList.last()) // stop
        }
    }

}
