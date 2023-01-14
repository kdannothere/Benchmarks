package com.kdan.benchmarks.ui.collections

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdan.benchmarks.data.Contract
import com.kdan.benchmarks.databinding.FragmentCollectionsBinding
import com.kdan.benchmarks.ui.BaseFragment
import com.kdan.benchmarks.ui.adapters.RecycleViewAdapter
import com.kdan.benchmarks.data.Callback
import com.kdan.benchmarks.ui.CollectionSizeDialogFragment

class CollectionsFragment : BaseFragment<FragmentCollectionsBinding>(
    FragmentCollectionsBinding::inflate
), Contract.View {

    private lateinit var presenter: CollsPresenter
    private lateinit var recyclerView: RecyclerView
    lateinit var adapter: RecycleViewAdapter
    private lateinit var handler: Handler
    private val tabNumber = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPresenter()
        presenter.setupFragment(this)
        if (presenter.firstLaunch) presenter.initialSetup()
        adapter = RecycleViewAdapter()
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = adapter
        handler = Handler(Looper.getMainLooper())
        adapter.submitList(presenter.items)

        setFragmentResultListener(presenter.tagCollectionSize + tabNumber) { _, bundle ->
            val result = bundle.getInt(presenter.tagCollectionSize)
            presenter.collectionSize = result
        }

        presenter.buttonText.observe(viewLifecycleOwner) {
            binding.buttonStartStop.text = it
        }

        binding.buttonStartStop.setOnClickListener {
            presenter.setElementsAmount()
            presenter.checkAndStart()
        }
    }

    override fun onResume() {
        super.onResume()
        CollectionSizeDialogFragment.currentTabNumber = tabNumber
        // call the Callback every second
        handler.postDelayed(Runnable {
            handler.postDelayed(presenter.tempThread!!, presenter.delay)
            if (Callback.Result.positionsCollections.isEmpty()) return@Runnable
            presenter.loadResult()
            presenter.update()
        }.also {
            presenter.tempThread = it
        }, presenter.delay)
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(presenter.tempThread!!)
    }

    override fun onStop() {
        super.onStop()
        presenter.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {
            putInt(presenter.tagCollectionSize, presenter.collectionSize)
            putInt(presenter.tagElementsAmount, presenter.elementsAmount)
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val savedCollectionSize = savedInstanceState?.getInt(presenter.tagCollectionSize).toString()
        val savedElementsAmount = savedInstanceState?.getInt(presenter.tagElementsAmount).toString()
        if (savedCollectionSize != "null") presenter.collectionSize = savedCollectionSize.toInt()
        if (savedElementsAmount != "null") presenter.elementsAmount = savedElementsAmount.toInt()
    }

    override fun setupPresenter() {
        presenter = when (Contract.SavedState.collsPresenter) {
            null -> CollsPresenter()
            else -> Contract.SavedState.collsPresenter!!
        }
    }

}
