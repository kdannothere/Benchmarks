package com.kdan.benchmarks.viewmodel


import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kdan.benchmarks.R
import com.kdan.benchmarks.repository.CollectionsRepository
import com.kdan.benchmarks.ui.CollectionSizeDialogFragment
import com.kdan.benchmarks.utility.Utility
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CollectionsViewModel : ViewModel(), Callback.LoadingResult {

    private val repository = CollectionsRepository()
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
    var needSetup = true

    fun setupItems() = repeat(21) { items += ItemData(id = it) }

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
                    Utility.checkCollectionSize(collectionSize) &&
                    Utility.checkElementsAmount(elementsAmount) &&
                    Utility.isGreaterOrEqual(collectionSize, elementsAmount)
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

    override fun loadResult() {
        positions.addAll(Callback.Result.positionsCollections)
        Callback.Result.positionsCollections.clear()
    }

    fun setupItemsInitialText(context: Context) {
        repeat(items.size) {
            val text: String = when (it) {
                0 -> context.getString(R.string.adding_beginning_array_list)
                1 -> context.getString(R.string.adding_middle_array_list)
                2 -> context.getString(R.string.adding_end_array_list)
                3 -> context.getString(R.string.search_by_value_array_list)
                4 -> context.getString(R.string.removing_beginning_array_list)
                5 -> context.getString(R.string.removing_middle_array_list)
                6 -> context.getString(R.string.removing_end_array_list)
                7 -> context.getString(R.string.adding_beginning_linked_list)
                8 -> context.getString(R.string.adding_middle_linked_list)
                9 -> context.getString(R.string.adding_end_linked_list)
                10 -> context.getString(R.string.search_by_value_linked_list)
                11 -> context.getString(R.string.removing_beginning_linked_list)
                12 -> context.getString(R.string.removing_middle_linked_list)
                13 -> context.getString(R.string.removing_end_linked_list)
                14 -> context.getString(R.string.adding_beginning_copy_on_write_array_list)
                15 -> context.getString(R.string.adding_middle_copy_on_write_array_list)
                16 -> context.getString(R.string.adding_end_copy_on_write_array_list)
                17 -> context.getString(R.string.search_by_value_copy_on_write_array_list)
                18 -> context.getString(R.string.removing_beginning_copy_on_write_array_list)
                19 -> context.getString(R.string.removing_middle_copy_on_write_array_list)
                20 -> context.getString(R.string.removing_end_copy_on_write_array_list)
                else -> "Android got lost LOL"
            }
            items[it].changeText(text, setInitialText = true)
        }
    }

    fun setupButtonTextList(context: Context) {
        buttonTextList.add(context.getString(R.string.button_start))
        buttonTextList.add(context.getString(R.string.button_stop))
        buttonText.postValue(buttonTextList.first())
    }

}