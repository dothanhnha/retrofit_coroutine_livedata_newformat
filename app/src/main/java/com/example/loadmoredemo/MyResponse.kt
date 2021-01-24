package com.example.loadmoredemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.loadmoredemo.Exception.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

data class MyResponse<out T>(
    val status: MyStatus,
    val data: T?,
    val exception: AbstractAppException?
) {
    companion object {
        val delayResponse: Long = 3000
        fun <T> success(data: T): MyResponse<T> =
            MyResponse(status = MyStatus.SUCCESS, data = data, exception = null)

        fun <T> error(data: T?, exception: AbstractAppException?): MyResponse<T> =
            MyResponse(status = MyStatus.ERROR, data = data, exception = exception)

        fun <T> loading(): MyResponse<T> =
            MyResponse(status = MyStatus.LOADING, data = null, exception = null)

        suspend fun <T> createMyReponse(funcExcuteApi: suspend () -> T): LiveData<MyResponse<out T>> {
            var result: LiveData<MyResponse<out T>> = liveData {
                emit(loading())
                withContext(Dispatchers.IO) {
                    try {
                        val response = funcExcuteApi()
                        delay(delayResponse)
                        emit(success(data = response))
                    } catch (throwable: Throwable) {
                        emit(MyResponse(MyStatus.ERROR, null, generateException(throwable)))
                    }
                }
            }
            return result
        }

        fun generateException(throwable: Throwable): AbstractAppException {
            if (throwable is HttpException) {
                return ApiServerException(throwable)
            } else if (throwable is UnknownHostException) {
                return ApiUnknownHostException(throwable)
            } else if (throwable is TimeoutException || throwable is SocketTimeoutException) {
                return ApiNetworkTimeoutException(throwable)
            } else if (throwable is ConnectException || throwable is IOException) {
                return ApiNetworkUnavailableException(throwable)
            } else {
                return ApiUnknownException(throwable)
            }
        }
    }

}

enum class MyStatus {
    SUCCESS,
    ERROR,
    LOADING
}