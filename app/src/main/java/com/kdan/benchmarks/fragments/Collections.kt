package com.kdan.benchmarks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdan.benchmarks.R
import com.kdan.benchmarks.adapters.CellAdapter
import com.kdan.benchmarks.data.Data
import com.kdan.benchmarks.databinding.FragmentCollectionsBinding
import com.kdan.benchmarks.functions.DoCollections
import kotlin.concurrent.thread

class Collections : Fragment() {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var cellAdapter: CellAdapter
    private lateinit var button: Button
    private lateinit var initialText: MutableList<String>
    private lateinit var result: MutableList<Int>
    private var position = 0

    companion object {
        private var collectionSize = 10000
        const val tagCollectionSize = "collectionSize"
        private var elementsAmount = 5000
        const val tagElementsAmount = "elementsAmount"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(this.context, 3)
        initialText = setInitialText()
        result = setInitialResult()
        Data.text = initialText
        Data.collectionSize = collectionSize
        cellAdapter = CellAdapter(text = Data.text)
        recyclerView.adapter = cellAdapter
        button = binding.buttonStartStop
        updateText()
        setFragmentResultListener(tagCollectionSize) { _, bundle ->
            val result = bundle.getInt(tagCollectionSize)
            collectionSize = result
        }
        binding.buttonStartStop.setOnClickListener { start() }
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
        changeButtonName()
        cellAdapter.notifyDataSetChanged()
        thread(name = "All operations") {
            DoCollections(collectionSize, elementsAmount, context).startAll()
            ++position
        }
        changeButtonName()
        cellAdapter.notifyDataSetChanged()
    }

    private fun changeButtonName() {
        if (button.text.toString() == getString(R.string.button_start)) {
            button.text = getString(R.string.button_stop)
            // getElementsAmount()
        } else {
            button.text = getString(R.string.button_start)
        }

        // updateResult()
    }

    private fun updateText() {
        for (index in 0..20) {
            Data.text[index] = "${initialText[index]} ${result[index]} ms"
        }
        cellAdapter.notifyDataSetChanged()
    }

    private fun updateResult() {
        for (index in 0..20) {
            result[index] = result[index] + 1
        }
        updateText()
    }

    private fun setInitialResult(): MutableList<Int> {
        return MutableList(21) { 0 }
    }

    private fun setInitialText(): MutableList<String> {
        val list = mutableListOf<String>()
        for (index in 0..20) {
            val str: String = when (index) {
                0 -> getString(R.string.adding_beginning_array_list)
                1 -> getString(R.string.adding_middle_array_list)
                2 -> getString(R.string.adding_end_array_list)
                3 -> getString(R.string.search_by_value_array_list)
                4 -> getString(R.string.removing_beginning_array_list)
                5 -> getString(R.string.removing_middle_array_list)
                6 -> getString(R.string.removing_end_array_list)
                7 -> getString(R.string.adding_beginning_linked_list)
                8 -> getString(R.string.adding_middle_linked_list)
                9 -> getString(R.string.adding_end_linked_list)
                10 -> getString(R.string.search_by_value_linked_list)
                11 -> getString(R.string.removing_beginning_linked_list)
                12 -> getString(R.string.removing_middle_linked_list)
                13 -> getString(R.string.removing_end_linked_list)
                14 -> getString(R.string.adding_beginning_copy_on_write_array_list)
                15 -> getString(R.string.adding_middle_copy_on_write_array_list)
                16 -> getString(R.string.adding_end_copy_on_write_array_list)
                17 -> getString(R.string.search_by_value_copy_on_write_array_list)
                18 -> getString(R.string.removing_beginning_copy_on_write_array_list)
                19 -> getString(R.string.removing_middle_copy_on_write_array_list)
                20 -> getString(R.string.removing_end_copy_on_write_array_list)
                else -> "Android got lost XD"
            }
            list += str
        }
        return list
    }

    private fun getElementsAmount() {
        //elementsAmount = binding.textInputElementsAmount.text.toString().toInt()
    }

    class Timer {
        val timer = 0
    }

}