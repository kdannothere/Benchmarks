package com.kdan.benchmarks.fragments.dialog_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.kdan.benchmarks.data.Data
import com.kdan.benchmarks.databinding.DialogFragmentCollectionSizeBinding

class CollectionSizeDialogFragment : DialogFragment() {

    private lateinit var binding: DialogFragmentCollectionSizeBinding

    /*
    companion object {
        const val TAG = "CollectionSizeDialogFragment"
    }
     */

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DialogFragmentCollectionSizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonCalculate.setOnClickListener { takeData() }
    }

    private fun takeData() {
        // CS - collection size
        val currentCS = binding.textInputCollectionSize.text.toString().toInt()
        Data.collectionSize = currentCS
        Data.dialogOn = false
        this.dismiss()
    }

}

/*
setFragmentResultListener("requestKey") { requestKey, bundle ->
    val result = bundle.getInt("bundleKey")
    if (result in ONE_MILLION..TEN_MILLIONS) {
        Collections.collectionSize = result
        super.dismissNow()
    } else {
        Toast.makeText(context, "text", Toast.LENGTH_LONG).show()
    }
}
*/