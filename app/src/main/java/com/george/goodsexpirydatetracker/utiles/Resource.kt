package com.george.goodsexpirydatetracker.utiles

import android.util.Log

data class Resource<T>(
    val success: Status,
    val data: T? = null,
    val message: String? = null
) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING,
        FAILURE
    }

    companion object {

        private const val TAG = "Resource"

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, data: T? = null): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> failed(message: String, data: T? = null): Resource<T> {
            return Resource(Status.FAILURE, data, message)
        }

    }

    fun handler(
        loading:()->Unit,
        error:(String)->Unit,
        failed:(String)->Unit,
        suc:(T)->Unit,
    ) {
        when (this.success) {
            Status.LOADING -> {
                loading()
                Log.d(TAG, "$TAG >>> LOADING")
            }
            Status.SUCCESS -> {
                suc(data!!)
                Log.d(TAG, "$TAG >>> SUCCESS $data")
            }
            Status.ERROR -> {
                error(message!!)
                Log.d(TAG, "$TAG >>> ERROR $message")
            }
            Status.FAILURE -> {
                failed(message!!)
                Log.d(TAG, "$TAG >>> FAILURE $message")
            }
        }
    }
    /*
    x.handler(
        err = x.data?.error,
        loading = {},
        error = {},
        failed = {},
        showActionDialog = { showSessionDialog() }
     ) {

     }
    */


}