package com.kdan.benchmarks.viewmodel

import android.os.Looper
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kdan.benchmarks.repository.MapsRepository
import com.kdan.benchmarks.ui.CollectionSizeDialogFragment
import com.kdan.benchmarks.utility.Checker
import java.lang.System.gc
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class MapsViewModel : ViewModel() {

    private val service: ExecutorService = Executors.newSingleThreadExecutor()
    private val repository = MapsRepository()
    var items = mutableListOf<ItemData>()
    var collectionSize = 1000000 // test
    val tagCollectionSize = CollectionSizeDialogFragment.tagCollectionSize
    var elementsAmount = 1000000 // test
    val tagElementsAmount = "elementsAmount"
    val buttonTextList = mutableListOf<String>()
    val buttonText = MutableLiveData<String>()
    val temp = mutableSetOf<Int>()
    var runnableMain: Runnable? = null
    val delay = 1000

    fun setupItems() {
        if (items.isNotEmpty()) return
        repeat(6) { items += ItemData(id = it) }
    }

    fun start() {
        if (repository.isRunning) {
            Log.d("SHOW", "stop")
            repository.isRunning = false
            changeButtonName(true)
            return
        }

        if (!repository.isStopped) return
        service.execute(
            Thread {
                Log.d("SHOW", "start")
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
