package com.testproject.android.kotlintestproject.interfaces

import com.testproject.android.kotlintestproject.model.Currency

interface AdapterInterface {
    fun completeListener(list: MutableList<Currency>)
}