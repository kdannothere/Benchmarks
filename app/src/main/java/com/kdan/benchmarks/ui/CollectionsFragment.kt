package com.kdan.benchmarks.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdan.benchmarks.R
import com.kdan.benchmarks.databinding.FragmentMapsBinding
import com.kdan.benchmarks.viewmodel.CollectionsViewModel

class CollectionsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecycleViewAdapter
    private val viewModel: CollectionsViewModel by viewModels()
    private lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        adapter = RecycleViewAdapter()
        viewModel.setupItems()
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(this.context, 3)
        recyclerView.adapter = adapter
        viewModel.updateButtonText(setupButtonText())
        setInitialText()
        button = binding.buttonStartStop
        observe()

        setFragmentResultListener(viewModel.tagCollectionSize) { _, bundle ->
            val result = bundle.getInt(viewModel.tagCollectionSize)
            viewModel.collectionSize = result
        }

        button.setOnClickListener {
            setElementsAmount()
            viewModel.start()
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

    private fun setElementsAmount() {
        val input = binding.textInputElementsAmount.text.toString()
        if (input.isNotBlank()) viewModel.elementsAmount = input.toInt()
    }

    private fun setInitialText() {
        if (viewModel.items.value!!.first().initialText.isNotEmpty()) return
        repeat (6) {
            val text: String = when (it) {
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
                else -> "Android got lost LOL"
            }
            viewModel.items.value?.get(it)?.changeText(text, setInitialText = true)
        }
    }

    private fun setupButtonText(): String {
        val list = MutableList(3) { "" }
        repeat(3) {
            val text: String = when (it) {
                0 -> getString(R.string.button_start)
                1 -> getString(R.string.button_start)
                2 -> getString(R.string.button_stop)
                else -> "Android got lost LOL"
            }
            list[it] = text
        }
        viewModel.buttonText = list
        return list.first()
    }

    private fun observe() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        viewModel.updater.observe(viewLifecycleOwner) { updater ->
            val temp = viewModel.repository.temp
            when {
                updater == true && viewModel.repository.currentOperation == -2 -> {
                    Log.d("SHOW", "2")
                    adapter.notifyItemChanged(temp)
                    if (temp == 5) viewModel.changeButtonName()
                    viewModel.updater.value = false
                }
                updater == true -> {
                    if (viewModel.repository.currentOperation == -1) {
                        viewModel.changeButtonName()
                        Log.d("SHOW", "3")
                    } else {
                        Log.d("SHOW", "1")
                        adapter.notifyItemChanged(temp)
                    }
                    viewModel.updater.value = false
                }
            }
        }

        viewModel.currentButtonText.observe(viewLifecycleOwner) {
            button.text = viewModel.buttonText.first()
        }
    }

}