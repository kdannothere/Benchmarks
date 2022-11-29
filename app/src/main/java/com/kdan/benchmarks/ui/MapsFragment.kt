package com.kdan.benchmarks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdan.benchmarks.R
import com.kdan.benchmarks.databinding.FragmentMapsBinding
import com.kdan.benchmarks.repository.MapsRepository
import com.kdan.benchmarks.viewmodel.MapsViewModel


class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    val viewModel: MapsViewModel by viewModels()
    lateinit var recyclerView: RecyclerView
    private lateinit var button: Button
    private lateinit var initialText: MutableList<String>
    var countClicks = 0 // for stopping
    val stop get() = countClicks % 2 == 0

    companion object {
        private var collectionSize = 0
        const val tagCollectionSize = "collectionSize"
        private var elementsAmount = 0
        const val tagElementsAmount = "elementsAmount"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(this.context, 3)
        val adapter = RecycleViewAdapter()
        recyclerView.adapter = adapter
        button = binding.buttonStartStop

        setFragmentResultListener(tagCollectionSize) { _, bundle ->
            val result = bundle.getInt(tagCollectionSize)
            collectionSize = result
        }

        binding.buttonStartStop.setOnClickListener { start() }

        viewModel.items.observe(viewLifecycleOwner) { items ->
            initialText = setInitialText()
            adapter.submitList(items)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(tagCollectionSize, collectionSize)
            putInt(tagElementsAmount, elementsAmount)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val savedCollectionSize = savedInstanceState?.getInt(tagCollectionSize).toString()
        val savedElementsAmount = savedInstanceState?.getInt(tagCollectionSize).toString()
        if (savedCollectionSize != "null") collectionSize = savedCollectionSize.toInt()
        if (savedCollectionSize != "null") elementsAmount = savedElementsAmount.toInt()
    }

    private fun start() {
        ++countClicks
        setElementsAmount()
        if (stop) {
            changeAllBars(true)
            changeButtonName(true)
            return
        }

        if (checkRange(elementsAmount)) {
            changeButtonName()
            changeAllBars()
            Thread {
                MapsRepository(collectionSize, elementsAmount, this).startAll()
            }.start()
        } else {
            Toast.makeText(context, R.string.check_range_elements_amount, Toast.LENGTH_SHORT).show()
        }
    }

    fun changeButtonName(stop: Boolean = false) {
        if (stop) button.text = getString(R.string.button_stop)

        if (button.text.toString() == getString(R.string.button_start)) {
            button.text = getString(R.string.button_stop)
        } else {
            button.text = getString(R.string.button_start)
        }
    }

    fun updateText(index: Int) {
        val result = viewModel.items.value?.get(index)?.result
        viewModel.changeText(index, "${initialText[index]} $result ms")
    }

    private fun setInitialText(): MutableList<String> {
        val list = MutableList(viewModel.items.value!!.size) { "" }
        for (index in 0..viewModel.items.value!!.lastIndex) {
            val text: String = when (index) {
                0 -> getString(R.string.adding_new_tree_map)
                1 -> getString(R.string.search_by_key_tree_map)
                2 -> getString(R.string.removing_tree_map)
                3 -> getString(R.string.adding_new_hash_map)
                4 -> getString(R.string.search_by_key_hash_map)
                5 -> getString(R.string.removing_hash_map)
                else -> "Android got lost LOL"
            }
            list[index] = text
            viewModel.changeText(index = index, newText = text)
        }
        return list
    }

    private fun setElementsAmount() {
        val input = binding.textInputElementsAmount.text.toString()
        if (input.isNotBlank()) elementsAmount = input.toInt()
    }

    private fun changeAllBars(stop: Boolean = false) {
        for (index in 0..viewModel.items.value!!.lastIndex) {
            viewModel.changeBar(index, stop)
            recyclerView.adapter?.notifyItemChanged(index)
        }
    }

    private fun checkRange(elementsAmount: Int): Boolean {
        return elementsAmount in 1000000..10000000
    }

    // need enum class State ?

}