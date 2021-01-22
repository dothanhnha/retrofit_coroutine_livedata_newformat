package com.example.loadmoredemo
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel {

    constructor(){
        setLoadingField()
    }

    var eventCallBackStatusResponse: OnStatusResponseChange? = null


    interface OnStatusResponseChange {
        fun observeReponse()
        fun onSuccessAll()
        fun onLoading()
        fun onError()
    }

    open protected fun fetchData() {
        setLoadingField()
    }
    fun refreshData(){
        fetchData()
        eventCallBackStatusResponse?.observeReponse()
    }

    open fun setup(owner: LifecycleOwner, eventCallBackStatusResponse: OnStatusResponseChange) {
        this.eventCallBackStatusResponse = eventCallBackStatusResponse
    }

    abstract fun setLoadingField()
}
