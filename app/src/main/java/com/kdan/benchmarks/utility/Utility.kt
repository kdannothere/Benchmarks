package com.kdan.benchmarks.utility

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utility {

    private val correctRange = 1000000..10000000

    fun checkCollectionSize(collectionSize: Int): Boolean {
        return collectionSize in correctRange
    }

    fun checkElementsAmount(elementsAmount: Int): Boolean {
        return elementsAmount in correctRange
    }

    fun isGreaterOrEqual(collectionSize: Int, elementsAmount: Int): Boolean {
        return collectionSize >= elementsAmount
    }

    fun hideKeyboard(context: Context, view: View) {
        val imm: InputMethodManager =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}