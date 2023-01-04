package com.kdan.benchmarks.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdan.benchmarks.MainActivity
import com.kdan.benchmarks.databinding.FragmentCollectionsBinding
import com.kdan.benchmarks.utility.Utility
import com.kdan.benchmarks.viewmodel.Callback
import com.kdan.benchmarks.viewmodel.CollectionsViewModel

class CollectionsFragment : Fragment() {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecycleViewAdapter
    private val viewModel: CollectionsViewModel by viewModels()
    private lateinit var handler: Handler

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        adapter = RecycleViewAdapter()
        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        if (viewModel.needSetup) {
            viewModel.setupButtonTextList(requireContext())
            viewModel.setupItems()
            viewModel.setupItemsInitialText(requireContext())
            viewModel.needSetup = false
        }
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(this.context, 3)
        recyclerView.adapter = adapter
        handler = Handler(Looper.getMainLooper())
        adapter.submitList(viewModel.items)

        setFragmentResultListener(viewModel.tagCollectionSize) { _, bundle ->
            val result = bundle.getInt(viewModel.tagCollectionSize)
            viewModel.collectionSize = result
        }

        viewModel.buttonText.observe(viewLifecycleOwner) {
            binding.buttonStartStop.text = it
        }

        binding.buttonStartStop.setOnClickListener {
            setElementsAmount()
            viewModel.start()
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        // call the Callback every second
        handler.postDelayed(Runnable {
            handler.postDelayed(viewModel.tempThread!!, viewModel.delay)
            if (Callback.Result.positionsCollections.isEmpty()) return@Runnable
            viewModel.loadResult()
            update()
        }.also { viewModel.tempThread = it
               }, viewModel.delay)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(viewModel.tempThread!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun update() {
        viewModel.positions.forEach { adapter.notifyItemChanged(it) }
        viewModel.positions.clear()
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
        when {
            input.isNotBlank() -> viewModel.elementsAmount = input.toInt()
            input.isBlank() -> {
                viewModel.elementsAmount = 0
                if (viewModel.collectionSize == 0) {  // Feature: call dialog
                    val activity = requireActivity() as MainActivity
                    activity.binding.floatingButton.performClick()
                }
            }
        }
        Utility.hideKeyboard(requireContext(), binding.buttonStartStop)
    }

}
