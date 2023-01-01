package com.kdan.benchmarks.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdan.benchmarks.MainActivity
import com.kdan.benchmarks.R
import com.kdan.benchmarks.databinding.FragmentMapsBinding
import com.kdan.benchmarks.viewmodel.Callback
import com.kdan.benchmarks.viewmodel.MapsViewModel
import kotlinx.coroutines.*

class MapsFragment : Fragment(), Callback.LoadingResult {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecycleViewAdapter
    private val viewModel: MapsViewModel by viewModels()
    private lateinit var handler: Handler
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
        handler = Handler(Looper.getMainLooper())
        setupButtonTextList()
        setupItemsInitialText()
        button = binding.buttonStartStop
        adapter.submitList(viewModel.items)

        setFragmentResultListener(viewModel.tagCollectionSize) { _, bundle ->
            val result = bundle.getInt(viewModel.tagCollectionSize)
            viewModel.collectionSize = result
        }

        viewModel.buttonText.observe(viewLifecycleOwner) {
            button.text = it
        }

        button.setOnClickListener {
            setElementsAmount()
            viewModel.start()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // call the Callback every second
        handler.postDelayed(Runnable {
            handler.postDelayed(viewModel.runnableMain!!, viewModel.delay.toLong())
            if (Callback.Result.temp.isEmpty()) return@Runnable
            loadResult()
            update()
        }.also { viewModel.runnableMain = it }, viewModel.delay.toLong())
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(viewModel.runnableMain!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun loadResult() {
        viewModel.temp.addAll(Callback.Result.temp)
        Callback.Result.temp.clear()
    }

    private fun update() {
        viewModel.temp.forEach { adapter.notifyItemChanged(it) }
        viewModel.temp.clear()
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
        when {
            input.isNotBlank() -> viewModel.elementsAmount = input.toInt()
            input.isBlank() -> {
                // viewModel.elementsAmount = 0 // test
                if (viewModel.collectionSize == 0) {  // Feature: call dialog
                    val activity = requireActivity() as MainActivity
                    activity.binding.floatingButton.performClick()
                }
            }
        }
        hideKeyboardFrom(requireContext(), button)
    }

    private fun setupItemsInitialText() {
        if (viewModel.items.first().initialText.isNotEmpty()) return
        repeat(viewModel.items.size) {
            val text: String = when (it) {
                0 -> getString(R.string.adding_new_tree_map)
                1 -> getString(R.string.search_by_key_tree_map)
                2 -> getString(R.string.removing_tree_map)
                3 -> getString(R.string.adding_new_hash_map)
                4 -> getString(R.string.search_by_key_hash_map)
                5 -> getString(R.string.removing_hash_map)
                else -> "Android got lost LOL"
            }
            viewModel.items[it].changeText(text, setInitialText = true)
        }
    }

    private fun setupButtonTextList() {
        if (viewModel.buttonTextList.isNotEmpty()) return
        viewModel.buttonTextList.add(getString(R.string.button_start))
        viewModel.buttonTextList.add(getString(R.string.button_stop))
        viewModel.buttonText.postValue(viewModel.buttonTextList.first())
    }

}
