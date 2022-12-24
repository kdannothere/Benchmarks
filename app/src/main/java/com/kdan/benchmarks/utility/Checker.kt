package com.kdan.benchmarks.utility

object Checker {

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

}