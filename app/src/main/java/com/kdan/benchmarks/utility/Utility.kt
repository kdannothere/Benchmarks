package com.kdan.benchmarks.utility

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.kdan.benchmarks.R

object Utility {

    private val correctRange = 1000000..10000000

    fun checkNumbers(context: Context, collectionSize: Int, elementsAmount: Int): Boolean {
        val collectionSizeIsOk = checkCollectionSize(collectionSize)
        val elementsAmountIsOk = checkElementsAmount(elementsAmount)
        val elementsAmountSuitsCollectionSize = isGreaterOrEqual(collectionSize, elementsAmount)
        when {
            !collectionSizeIsOk -> showToast(context, context.getString(R.string.collection_size_wrong))
            !elementsAmountSuitsCollectionSize -> showToast(context, context.getString(R.string.elements_amount_doesnt_suit))
            !elementsAmountIsOk -> showToast(context, context.getString(R.string.elements_amount_wrong))
            else -> return true
        }
        return false
    }

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

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}