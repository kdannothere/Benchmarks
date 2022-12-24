package com.kdan.benchmarks.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kdan.benchmarks.repository.MapsRepository
import com.kdan.benchmarks.ui.CollectionSizeDialogFragment
import com.kdan.benchmarks.utility.Checker

class MapsViewModel : ViewModel() {

    val items = MutableLiveData<List<ItemData>>()
    val needUpdate = MutableLiveData<Boolean>() // updates items in the adapter if value is true
    var collectionSize = 3000000
    val tagCollectionSize = CollectionSizeDialogFragment().tagCollectionSize
    var elementsAmount = 3000000
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
        needUpdate.postValue(false)
    }

    fun start(context: Context) {
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
                        changeButtonName()
                        prepRep()
                        repository.startAll()
                    }
            }.start()
            return
        }
        Toast.makeText(context, "Wait the second, please:)", Toast.LENGTH_SHORT).show()
    }



    // prepare repository
    private fun prepRep() {
        repository.let {
            it.collectionSize = collectionSize
            it.elementsAmount = elementsAmount
            it.items = items
            it.needUpdate = needUpdate
        }
    }

    fun changeButtonName(stop: Boolean = false) {
        if (stop) {
            buttonText[0] = buttonText[1] // text = start
        } else {
            buttonText[0] = buttonText[2] // text = stop
        }
    }

}
