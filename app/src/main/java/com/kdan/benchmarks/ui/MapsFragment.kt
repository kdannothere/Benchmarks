package com.kdan.benchmarks.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdan.benchmarks.MainActivity
import com.kdan.benchmarks.R
import com.kdan.benchmarks.databinding.FragmentMapsBinding
import com.kdan.benchmarks.viewmodel.MapsViewModel

class MapsFragment : Fragment() {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecycleViewAdapter
    private val viewModel: MapsViewModel by viewModels()
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
        setupButtonText()
        setupItemsInitialText()
        button = binding.buttonStartStop
        button.text = viewModel.buttonText.first()
        observe()

        setFragmentResultListener(viewModel.tagCollectionSize) { _, bundle ->
            val result = bundle.getInt(viewModel.tagCollectionSize)
            viewModel.collectionSize = result
        }

        button.setOnClickListener {
            // setElementsAmount()
            viewModel.start(requireContext())
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

    private fun hideKeyboardFrom(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun setElementsAmount() {
        val input = binding.textInputElementsAmount.text.toString()
        if (input.isNotBlank()) {
            viewModel.elementsAmount = input.toInt()
        } else {
            val activity = requireActivity() as MainActivity
            activity.binding.floatingButton.performClick()
        }
        hideKeyboardFrom(requireContext(), button)
    }

    private fun setupItemsInitialText() {
        if (viewModel.items.value!!.first().initialText.isNotEmpty()) return
        repeat(6) {
            val text: String = when (it) {
                0 -> getString(R.string.adding_new_tree_map)
                1 -> getString(R.string.search_by_key_tree_map)
                2 -> getString(R.string.removing_tree_map)
                3 -> getString(R.string.adding_new_hash_map)
                4 -> getString(R.string.search_by_key_hash_map)
                5 -> getString(R.string.removing_hash_map)
                else -> "Android got lost LOL"
            }
            viewModel.items.value?.get(it)?.changeText(text, setInitialText = true)
        }
    }

    private fun setupButtonText() {
        if (viewModel.buttonText.isNotEmpty()) return
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
    }

    private fun observe() {
        viewModel.items.observe(viewLifecycleOwner) { items ->
            adapter.submitList(items)
        }

        viewModel.needUpdate.observe(viewLifecycleOwner) { updater ->
            if (updater) {
                when {
                    viewModel.repository.isDone -> {
                        viewModel.changeButtonName(true)
                        button.text = viewModel.buttonText.first() // button.text = start
                        updateCell()
                    }
                    viewModel.repository.isRunning -> {
                        updateCell()
                        if (viewModel.repository.currentOperation == 0) {
                            button.text = viewModel.buttonText.first() // button.text = stop
                            updateCell()
                        }
                    }
                }
                viewModel.needUpdate.value = false
            }
        }
    }

    private fun updateCell() {
        val temp = mutableSetOf<Int>()
        temp.addAll(viewModel.repository.temp)
        temp.forEach {
            adapter.notifyItemChanged(it)
            viewModel.repository.temp.remove(it)
        }
        temp.clear()
    }

    interface MapsCallback {
        fun onSuccess()
        fun onFailure()
    }

}
