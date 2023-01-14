package com.kdan.benchmarks.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.kdan.benchmarks.R
import com.kdan.benchmarks.databinding.DialogFragmentCollectionSizeBinding
import com.kdan.benchmarks.utility.Utility

class CollectionSizeDialogFragment : DialogFragment() {

    private var _binding: DialogFragmentCollectionSizeBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val tagCollectionSize = "collectionSize"
        var currentTabNumber = 0
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DialogFragmentCollectionSizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCalculate.setOnClickListener { takeData() }
    }

    private fun takeData() {
        val input = binding.textInputCollectionSize.text.toString().toIntOrNull()
        if (input == null || !Utility.checkCollectionSize(collectionSize = input)) {
            Utility.showToast(requireContext(), getString(R.string.collection_size_wrong))
            return
        }
        setFragmentResult(tagCollectionSize + currentTabNumber, bundleOf(tagCollectionSize to input))
        dismiss()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(tagCollectionSize, binding.textInputCollectionSize.text.toString().toInt())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val collectionSize = savedInstanceState?.getInt(tagCollectionSize).toString()
        val currentText = binding.textInputCollectionSize.text.toString()
        binding.textInputCollectionSize.text?.replace(0, currentText.length, collectionSize)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}