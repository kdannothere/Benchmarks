package com.kdan.benchmarks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdan.benchmarks.adapters.CellAdapter
import com.kdan.benchmarks.databinding.FragmentCollectionsBinding


class Collections : Fragment() {

    private lateinit var binding: FragmentCollectionsBinding

    companion object {
        private var collectionSize = 0
        private lateinit var recyclerView: RecyclerView
        private var isLinearLayoutManager = true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        chooseLayout()
        setFragmentResultListener("collectionSize") { requestKey, bundle ->
            val result = bundle.getInt("collectionSize")
            collectionSize = result
            binding.textViewTest.text = collectionSize.toString()
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val collectionSize = savedInstanceState?.getInt("collectionSize").toString()
        if (collectionSize == "null") return
        binding.textViewTest.text = collectionSize
    }

    private fun chooseLayout() {
        if (isLinearLayoutManager) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this.context, 3)
        }
        recyclerView.adapter = CellAdapter()
    }


}