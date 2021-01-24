package com.example.loadmoredemo.Exception

import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException

class APIError(override val message: String, val code: Int) : Throwable() {

    val isCodeUnknown: Boolean
        get() = code == CODE_UNKNOWN

    companion object {
        const val ERROR_UNKNOWN = "오류가 발생하였습니다. 다시 한번 시도해 주세요"
        const val CODE_UNKNOWN = -99
        ///custom to show right message error : base on back end:
        fun from(throwable: Throwable?): APIError {
            try {
                if (throwable != null) {
                    if (throwable is HttpException) {
                        val errorException = throwable
                        if (errorException.response() != null) {
                            val errorBody = errorException.response()!!.errorBody()
                            if (errorBody != null) {
                                return from(errorBody, errorException.code())
                            }
                        }
                        val http_status = errorException.code()
                        val http_error = errorException.message()
                        return APIError(http_error, http_status)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return APIError(ERROR_UNKNOWN, CODE_UNKNOWN)
        }

        fun from(errorBody: ResponseBody?, code: Int): APIError {
            try {
                if (errorBody != null) {
                    val joError = JSONObject(errorBody.string())
                    val msg = joError.optString("errors", "")
                    return APIError(msg, code)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return APIError(ERROR_UNKNOWN, CODE_UNKNOWN)
        }

        /*    public void printLog() {
        if (BuildConfig.DEBUG) {
            printStackTrace();
        }
    }*/
    }

}