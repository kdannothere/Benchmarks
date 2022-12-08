package com.kdan.benchmarks.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdan.benchmarks.R
import com.kdan.benchmarks.databinding.FragmentMapsBinding
import com.kdan.benchmarks.viewmodel.MapsViewModel

class MapsFragment : Fragment(), MapsInterface {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    override val viewModel = MapsViewModel()
    private lateinit var recyclerView: RecyclerView
    private val adapter = RecycleViewAdapter()
    private lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        viewModel.setupItems()
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(this.context, 3)
        recyclerView.adapter = adapter
        viewModel.updateButton(setupButtonText())
        viewModel.initialText = setInitialText()
        button = binding.buttonStartStop

        setFragmentResultListener(viewModel.tagCollectionSize) { _, bundle ->
            val result = bundle.getInt(viewModel.tagCollectionSize)
            viewModel.collectionSize = result
        }

        binding.buttonStartStop.setOnClickListener { start() }

        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        viewModel.currentButtonText.observe(viewLifecycleOwner) {
            button.text = viewModel.buttonText.first()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(viewModel.tagCollectionSize, viewModel.collectionSize)
            putInt(viewModel.tagElementsAmount, viewModel.elementsAmount)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val savedCollectionSize = savedInstanceState?.getInt(viewModel.tagCollectionSize).toString()
        val savedElementsAmount = savedInstanceState?.getInt(viewModel.tagElementsAmount).toString()
        if (savedCollectionSize != "null") viewModel.collectionSize = savedCollectionSize.toInt()
        if (savedElementsAmount != "null") viewModel.elementsAmount = savedElementsAmount.toInt()
    }

    private fun start() {
        //setElementsAmount()
        viewModel.stop = !viewModel.stop
        if (viewModel.stop) {
            viewModel.changeAllBars(true)
            viewModel.changeButtonName()
            return
        }

        if (checkRange()) {
            viewModel.changeButtonName(true)
            viewModel.changeAllBars()
            viewModel.calculation.startAll()
        } else {
            viewModel.stop = true
            Toast.makeText(context, R.string.check_range_elements_amount, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setElementsAmount() {
        val input = binding.textInputElementsAmount.text.toString()
        if (input.isNotBlank()) viewModel.elementsAmount = input.toInt()
    }

    private fun setInitialText(): MutableList<String> {
            val list = MutableList(6) { "" }
            for (index in 0..5) {
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
                viewModel.changeText(index, text)
            }
        return list
    }

    private fun checkRange(): Boolean {
        val correctRange = 1000000..10000000
        if (viewModel.collectionSize in correctRange &&
            viewModel.elementsAmount in correctRange &&
            viewModel.elementsAmount <= viewModel.collectionSize) return true
        return false
    }

    private fun setupButtonText(): MutableList<String> {
        val list = MutableList(3) { "" }
        for (index in 0..2) {
            val text: String = when (index) {
                0 -> getString(R.string.button_start)
                1 -> getString(R.string.button_start)
                2 -> getString(R.string.button_stop)
                else -> "Android got lost LOL"
            }
            list[index] = text
        }
        viewModel.buttonText = list
        return list
    }

}