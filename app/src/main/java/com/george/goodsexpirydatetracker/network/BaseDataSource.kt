package com.george.goodsexpirydatetracker.network

import android.util.Log
import com.george.goodsexpirydatetracker.utiles.InternetConnection.hasInternetConnection
import com.george.goodsexpirydatetracker.utiles.Resource
import retrofit2.Response
import java.io.IOException


@Suppress("LiftReturnOrAssignment")
abstract class BaseDataSource {

    companion object {
        const val TAG = "BaseDataSource"
    }

    // safe api call
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Resource<T> {
        try {

            if (hasInternetConnection()) {

                val response = apiCall()

                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d(TAG, "safeApiCall: body >>> $body")

                    if (body != null) {

                        return Resource.success(body)
                    }

                } else {

                    return Resource.error(response.errorBody()?.charStream().toString())

                }

                return Resource.failed("Something went wrong, try again")


            } else {

                return Resource.failed("No Internet Connection")

            }


        } catch (t: Throwable) {

            when (t) {
                is IOException -> return Resource.failed("Network Failure")
                else -> {
                    Log.e(TAG, "Conversion Error ${t.stackTraceToString()}", )
                    return Resource.failed("Conversion Error")
                }
            }

        }
    }

}