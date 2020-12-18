package com.fgdc.marvelcharacters.utils.exception

import com.squareup.moshi.Moshi
import okhttp3.ResponseBody

object ErrorHandler {

    const val NETWORK_ERROR_MESSAGE =
        "Please check your internet connection and try again!"
    const val UNKNOWN_ERROR = "An error occurred, please try again later"
    const val BAD_REQUEST = "There seems to be a problem with the request!"

    inline fun <reified T> parseError(responseBody: ResponseBody?): T? {
        val adapter = Moshi.Builder().build().adapter(T::class.java)

        val response = responseBody?.string()
        if (response != null)
            try {
                return adapter.fromJson(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        return null
    }
}
