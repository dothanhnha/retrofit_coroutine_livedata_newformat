package com.example.retrofitcouroutinelivedatanewformat

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.retrofitcouroutinelivedatanewformat.Exception.AbstractAppException
import kotlinx.coroutines.flow.*
import kotlin.concurrent.fixedRateTimer

abstract class BaseViewModel : ViewModel() {


    var eventCallBackStatusResponse: OnStatusResponseChange? = null


    interface OnStatusResponseChange {
        fun observeReponse()
        fun onLoading()
        fun onError()
    }

    fun refreshData() {
        fetchData()
        eventCallBackStatusResponse?.observeReponse()
    }

    open fun setup(owner: LifecycleOwner, eventCallBackStatusResponse: OnStatusResponseChange) {
        this.eventCallBackStatusResponse = eventCallBackStatusResponse
    }

    fun groupField(vararg input: Flow<MyResponse<Any?>>): Flow<MyResponse<Any?>> {
        var tempFlow: Flow<MyResponse<Any?>>

        tempFlow = input[0].combine(input[1]) { field1, field2 ->
            combineStatus(field1, field2)
        }.catch {
            emit(MyResponse.error(null,it as AbstractAppException))
        }

        for (i in 2..input.size - 1) {
            tempFlow = tempFlow.combine(input[i]) { field1, field2 ->
                combineStatus(field1, field2)
            }.catch {
                emit(MyResponse.error(null,it as AbstractAppException))
            }
        }
        return tempFlow
    }

    private fun combineStatus(field1: MyResponse<Any?>, field2: MyResponse<Any?>): MyResponse<Any?> {
        return if (field1.status == MyStatus.SUCCESS && field2.status == MyStatus.SUCCESS) {
            MyResponse.success(null)
        } else if (field1.status == MyStatus.ERROR) {
            throw field1.exception!!
        } else if (field2.status == MyStatus.ERROR) {
            throw field2.exception!!
        } else {
            MyResponse.loading(null)
        }
    }

    abstract fun fetchData()
}

