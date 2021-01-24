package com.example.loadmoredemo.Exception

import android.util.Log

open class ApiServerException : AbstractAppException {

    var serverError: APIError

    constructor(cause: Throwable?) : super(cause) {
        serverError = APIError.from(cause)
    }

    val serverErrorMessage: String?
        get() = serverError.message

    val serverErrorCode: Int
        get() = serverError.code ?: APIError.CODE_UNKNOWN

    fun debugErrorMessage(): String {
        return if (serverError.code != APIError.CODE_UNKNOWN) String.format(
            "code(%s), err(%s)",
            serverError.code,
            serverError.message
        ) else Log.getStackTraceString(this)
    }
}