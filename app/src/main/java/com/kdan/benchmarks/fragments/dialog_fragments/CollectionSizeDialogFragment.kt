package com.kdan.benchmarks.fragments.dialog_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.kdan.benchmarks.databinding.DialogFragmentCollectionSizeBinding

class CollectionSizeDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentCollectionSizeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = DialogFragmentCollectionSizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCalculate.setOnClickListener { takeData() }
    }

    private fun takeData() {
        val input = binding.textInputCollectionSize.text.toString().toIntOrNull()
        if (input !in correctRange || input == null) {
            Toast.makeText(this.context, "Error. Try another number.", Toast.LENGTH_LONG).show()
            return
        }
        setFragmentResult(COLLECTION_SIZE, bundleOf(COLLECTION_SIZE to input))
        this.dismiss()
    }

    companion object {
        const val COLLECTION_SIZE = "collectionSize"
        val correctRange = 1000000..10000000
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.run {
            putInt(COLLECTION_SIZE, binding.textInputCollectionSize.text.toString().toInt())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        val collectionSize = savedInstanceState?.getInt("collectionSize").toString()
        val currentText = binding.textInputCollectionSize.text.toString()
        binding.textInputCollectionSize.text?.replace(0, currentText.length, collectionSize)
    }

}