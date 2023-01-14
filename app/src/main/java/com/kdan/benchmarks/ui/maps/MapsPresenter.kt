package com.kdan.benchmarks.ui.maps

import androidx.lifecycle.MutableLiveData
import com.kdan.benchmarks.MainActivity
import com.kdan.benchmarks.R
import com.kdan.benchmarks.repository.MapsRepository
import com.kdan.benchmarks.ui.CollectionSizeDialogFragment
import com.kdan.benchmarks.utility.Utility
import com.kdan.benchmarks.data.Callback
import com.kdan.benchmarks.data.Contract
import com.kdan.benchmarks.data.ItemData
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MapsPresenter : Callback.LoadingResult, Contract.Presenter {

    private lateinit var fragment: MapsFragment
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

    fun setupFragment(fragment: MapsFragment) {
        this.fragment = fragment
    }

    fun initialSetup() {
        firstLaunch = false
        setupButtonTextList()
        setupItems()
        setupItemsInitialText()
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
                0 -> fragment.getString(R.string.adding_new_tree_map)
                1 -> fragment.getString(R.string.search_by_key_tree_map)
                2 -> fragment.getString(R.string.removing_tree_map)
                3 -> fragment.getString(R.string.adding_new_hash_map)
                4 -> fragment.getString(R.string.search_by_key_hash_map)
                5 -> fragment.getString(R.string.removing_hash_map)
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
        Contract.SavedState.mapsPresenter = this
    }

}