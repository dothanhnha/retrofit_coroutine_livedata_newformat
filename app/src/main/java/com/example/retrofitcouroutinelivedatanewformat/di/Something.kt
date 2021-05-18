package com.example.retrofitcouroutinelivedatanewformat.di

import android.util.Log
import javax.inject.Inject


class Something @Inject constructor() {
    init {
        Log.d("HILT", "new something")
    }
}