package com.kdan.benchmarks.ui.collections

import androidx.lifecycle.MutableLiveData
import com.kdan.benchmarks.data.Contract
import com.kdan.benchmarks.MainActivity
import com.kdan.benchmarks.R
import com.kdan.benchmarks.repository.CollectionsRepository
import com.kdan.benchmarks.ui.CollectionSizeDialogFragment
import com.kdan.benchmarks.utility.Utility
import com.kdan.benchmarks.data.Callback
import com.kdan.benchmarks.data.ItemData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CollsPresenter : Callback.LoadingResult, Contract.Presenter {

    private lateinit var fragment: CollectionsFragment
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
    var firstLaunch = true

    fun setupFragment(fragment: CollectionsFragment) {
        this.fragment = fragment
    }

    fun initialSetup() {
        firstLaunch = false
        setupButtonTextList()
        setupItems()
        setupItemsInitialText()
    }

    private fun setupItems() = repeat(21) { items += ItemData(id = it) }

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

    fun checkAndStart() {
        if (Utility.checkNumbers(
                fragment.requireContext(),
                collectionSize,
                elementsAmount)
        ) {
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
        positions.addAll(Callback.Result.positionsCollections)
        Callback.Result.positionsCollections.clear()
    }

    fun update() {
        positions.forEach { fragment.adapter.notifyItemChanged(it) }
        positions.clear()
    }

    fun setElementsAmount() {
        val input = fragment.binding.textInputElementsAmount.text.toString()
        when {
            input.isNotBlank() -> elementsAmount = input.toInt()
            input.isBlank() -> {
                elementsAmount = 0
                if (collectionSize == 0) {  // Feature: call dialog
                    val activity = fragment.requireActivity() as MainActivity
                    activity.binding.floatingButton.performClick()
                }
            }
        }
        Utility.hideKeyboard(fragment.requireContext(), fragment.binding.buttonStartStop)
    }

    private fun setupItemsInitialText() {
        repeat(items.size) {
            val text: String = when (it) {
                0 -> fragment.getString(R.string.adding_beginning_array_list)
                1 -> fragment.getString(R.string.adding_middle_array_list)
                2 -> fragment.getString(R.string.adding_end_array_list)
                3 -> fragment.getString(R.string.search_by_value_array_list)
                4 -> fragment.getString(R.string.removing_beginning_array_list)
                5 -> fragment.getString(R.string.removing_middle_array_list)
                6 -> fragment.getString(R.string.removing_end_array_list)
                7 -> fragment.getString(R.string.adding_beginning_linked_list)
                8 -> fragment.getString(R.string.adding_middle_linked_list)
                9 -> fragment.getString(R.string.adding_end_linked_list)
                10 -> fragment.getString(R.string.search_by_value_linked_list)
                11 -> fragment.getString(R.string.removing_beginning_linked_list)
                12 -> fragment.getString(R.string.removing_middle_linked_list)
                13 -> fragment.getString(R.string.removing_end_linked_list)
                14 -> fragment.getString(R.string.adding_beginning_copy_on_write_array_list)
                15 -> fragment.getString(R.string.adding_middle_copy_on_write_array_list)
                16 -> fragment.getString(R.string.adding_end_copy_on_write_array_list)
                17 -> fragment.getString(R.string.search_by_value_copy_on_write_array_list)
                18 -> fragment.getString(R.string.removing_beginning_copy_on_write_array_list)
                19 -> fragment.getString(R.string.removing_middle_copy_on_write_array_list)
                20 -> fragment.getString(R.string.removing_end_copy_on_write_array_list)
                else -> "Android got lost LOL"
            }
            items[it].changeText(text, setInitialText = true)
        }
    }

    private fun setupButtonTextList() {
        buttonTextList.add(fragment.getString(R.string.button_start))
        buttonTextList.add(fragment.getString(R.string.button_stop))
        buttonText.postValue(buttonTextList.first())
    }

    override fun onDestroy() {
        Contract.SavedState.collsPresenter = this
    }

}