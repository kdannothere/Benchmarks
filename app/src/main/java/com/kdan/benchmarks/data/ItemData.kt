package com.kdan.benchmarks.data

import android.view.View

class ItemData(
    val id: Int,
    var text: String = "",
    private var result: Int = 0,
    var visibilityOfBar: Int = View.GONE,
    var initialText: String = "",
) {
    fun changeText(newText: String, setInitialText: Boolean = false) {
        text = newText
        if (setInitialText) initialText = text
    }

    fun changeResult(newResult: Int) {
        result = newResult
    }

    fun changeBar(stop: Boolean = false) {
        visibilityOfBar = if (stop) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    fun updateText() {
        changeText("$initialText $result ms")
    }

}
