package com.kdan.benchmarks.viewmodel

import android.view.View

data class ItemData(
    val id: Int,
    var text: String,
    var result: Int = 0,
    var visibilityOfBar: Int = View.GONE
)
