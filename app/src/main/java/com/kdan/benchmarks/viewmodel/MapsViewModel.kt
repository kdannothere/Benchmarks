package com.kdan.benchmarks.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kdan.benchmarks.R
import com.kdan.benchmarks.repository.MapsRepository
import com.kdan.benchmarks.ui.CollectionSizeDialogFragment
import com.kdan.benchmarks.utility.Utility
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MapsViewModel : ViewModel(), Callback.LoadingResult {

    private val repository = MapsRepository()
    private val service: ExecutorService = Executors.newSingleThreadExecutor()
    var items = mutableListOf<ItemData>()
    var collectionSize = 0
    val tagCollectionSize = CollectionSizeDialogFragment.tagCollectionSize
    var elementsAmount = 0
    val tagElementsAmount = "elementsAmount"
    private val buttonTextList = mutableListOf<String>()
    val buttonText = MutableLiveData<String>()
    val positions = mutableSetOf<Int>()
    var tempThread: Runnable? = null
    val delay = 1000L
    var firstLaunch = true

    fun initialSetup(context: Context) {
        firstLaunch = false
        setupItems()
        setupButtonTextList(context)
        setupItemsInitialText(context)
    }

    private fun setupItems() = repeat(6) { items += ItemData(id = it) }

    private fun start() {
        if (repository.isRunning) {
            repository.isRunning = false
            return
        }

        if (!repository.isStopped) return
        service.execute(
            Thread {
                changeButtonName(false)
                prepRep()
                repository.startAll()
                changeButtonName(true)
            }
        )
    }

    fun checkAndStart(context: Context) {
        if (Utility.checkNumbers(
                context,
                collectionSize,
                elementsAmount)) {
            // start if numbers are okay
            start()
        } else {
            // stop execution just in case it's running
            repository.isRunning = false
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

    override fun loadResult() {
        positions.addAll(Callback.Result.positionsMaps)
        Callback.Result.positionsMaps.clear()
    }

    private fun setupItemsInitialText(context: Context) {
        repeat(items.size) {
            val text: String = when (it) {
                0 -> context.getString(R.string.adding_new_tree_map)
                1 -> context.getString(R.string.search_by_key_tree_map)
                2 -> context.getString(R.string.removing_tree_map)
                3 -> context.getString(R.string.adding_new_hash_map)
                4 -> context.getString(R.string.search_by_key_hash_map)
                5 -> context.getString(R.string.removing_hash_map)
                else -> "Android got lost LOL"
            }
            items[it].changeText(text, setInitialText = true)
        }
    }

    private fun setupButtonTextList(context: Context) {
        buttonTextList.add(context.getString(R.string.button_start))
        buttonTextList.add(context.getString(R.string.button_stop))
        buttonText.postValue(buttonTextList.first())
    }

}
