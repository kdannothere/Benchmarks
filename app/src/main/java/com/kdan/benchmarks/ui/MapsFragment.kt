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

class MapsFragment : Fragment(), Callback.LoadingResult {

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecycleViewAdapter
    private val viewModel: MapsViewModel by viewModels()
    private lateinit var handler: Handler
    private var runnable: Runnable? = null
    private val delay = 1000
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
        setupButtonText()
        setupItemsInitialText()
        button = binding.buttonStartStop
        adapter.submitList(viewModel.items)

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

    override fun onResume() {
        super.onResume()
        button.text = viewModel.buttonText[0]
        // call the Callback every second
        handler.postDelayed(Runnable {
            handler.postDelayed(runnable!!, delay.toLong())
            if (Callback.Result.temp.isEmpty()) return@Runnable
            loadResult()
            update()
            button.text = viewModel.buttonText[0]
        }.also { runnable = it }, delay.toLong())
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable!!)
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
        if (input.isNotBlank()) {
            viewModel.elementsAmount = input.toInt()
        } else {
            val activity = requireActivity() as MainActivity
            activity.binding.floatingButton.performClick()
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

}
