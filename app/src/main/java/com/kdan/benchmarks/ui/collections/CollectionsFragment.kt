package com.kdan.benchmarks.ui.collections

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdan.benchmarks.MainActivity
import com.kdan.benchmarks.databinding.FragmentCollectionsBinding
import com.kdan.benchmarks.ui.BaseFragment
import com.kdan.benchmarks.ui.CollectionSizeDialogFragment
import com.kdan.benchmarks.ui.adapters.RecycleViewAdapter
import com.kdan.benchmarks.utility.Utility
import com.kdan.benchmarks.viewmodel.Callback
import com.kdan.benchmarks.viewmodel.CollectionsViewModel

class CollectionsFragment : BaseFragment<FragmentCollectionsBinding>(
    FragmentCollectionsBinding::inflate
) {

    val viewModel: CollectionsViewModel by viewModels(
        factoryProducer = { factory }
    )
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecycleViewAdapter
    private lateinit var handler: Handler
    private val tabNumber = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (viewModel.firstLaunch) viewModel.initialSetup(requireContext())
        adapter = RecycleViewAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter
        handler = Handler(Looper.getMainLooper())
        adapter.submitList(viewModel.items)

        setFragmentResultListener(viewModel.tagCollectionSize + tabNumber) { _, bundle ->
            val result = bundle.getInt(viewModel.tagCollectionSize)
            viewModel.collectionSize = result
        }

        viewModel.buttonText.observe(viewLifecycleOwner) {
            binding.buttonStartStop.text = it
        }

        binding.buttonStartStop.setOnClickListener {
            setElementsAmount()
            viewModel.checkAndStart(requireContext())
        }
    }

    override fun onResume() {
        super.onResume()
        CollectionSizeDialogFragment.currentTabNumber = tabNumber
        // call the Callback every second
        handler.postDelayed(Runnable {
            handler.postDelayed(viewModel.tempThread!!, viewModel.delay)
            if (Callback.Result.positionsCollections.isEmpty()) return@Runnable
            viewModel.loadResult()
            update()
        }.also {
            viewModel.tempThread = it
        }, viewModel.delay)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(viewModel.tempThread!!)
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
