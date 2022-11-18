package com.kdan.benchmarks.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kdan.benchmarks.R

class CellAdapter(private val text: Array<String>) :
    RecyclerView.Adapter<CellAdapter.CellViewHolder>() {

    class CellViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.cell_text)!!
        // val bar = view.findViewById<ProgressBar>(R.id.cell_bar)
    }

    override fun getItemCount(): Int {
        return 21
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        // Setup custom accessibility delegate to set the text read
        layout.accessibilityDelegate = Accessibility
        return CellViewHolder(layout)
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        val item = text[position]
        holder.textView.text = item
    }

    companion object Accessibility : View.AccessibilityDelegate() {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfo,
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            /* I have no idea what the comment below means and what the code is for.
             Maybe I will deal with it later:)
             */

            /* With `null` as the second argument to [AccessibilityAction], the
            accessibility service announces "double tap to activate".
            If a custom string is provided,
            it announces "double tap to <custom string>".
             */
            val customString = host.context?.getString(R.string.text_test_1)
            val customClick =
                AccessibilityNodeInfo.AccessibilityAction(
                    AccessibilityNodeInfo.ACTION_CLICK,
                    customString
                )
            info.addAction(customClick)
        }
    }
}