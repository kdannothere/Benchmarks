package com.kdan.benchmarks.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.kdan.benchmarks.R
import com.kdan.benchmarks.data.Data

class CellAdapter(var text: MutableList<String>) :
    RecyclerView.Adapter<CellAdapter.CellViewHolder>() {

    inner class CellViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.cell_text)
        val bar: CircularProgressIndicator = view.findViewById(R.id.cell_bar)
    }

    override fun getItemCount(): Int {
        return text.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CellAdapter.CellViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_view, parent, false
        )
        return CellViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CellAdapter.CellViewHolder, position: Int) {
        val textView = holder.textView
        val bar = holder.bar
        textView.text = text[position]
        textView.doAfterTextChanged {
            if (bar.visibility == View.GONE) {
                bar.visibility = View.VISIBLE
            } else {
                bar.visibility = View.GONE
            }
        }
    }
}