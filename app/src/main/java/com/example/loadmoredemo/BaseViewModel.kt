package com.example.loadmoredemo
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {


    var eventCallBackStatusResponse: OnStatusResponseChange? = null


    interface OnStatusResponseChange {
        fun observeReponse()
        fun onLoading()
        fun onError()
    }

    fun refreshData(){
        fetchData()
        eventCallBackStatusResponse?.observeReponse()
    }

    open fun setup(owner: LifecycleOwner, eventCallBackStatusResponse: OnStatusResponseChange) {
        this.eventCallBackStatusResponse = eventCallBackStatusResponse
    }

    abstract fun fetchData()
}
