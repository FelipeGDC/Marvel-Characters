package com.fgdc.marvelcharacters.utils.exception

import android.content.Context
import android.widget.Toast
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody

object ErrorHandler {

    const val NETWORK_ERROR_MESSAGE =
        "Please check your internet connectivity and try again!"
    const val UNKNOWN_ERROR = "An unknown error occurred!"

    private fun showLongToast(context: Context, message: String) = Toast.makeText(
        context,
        message,
        Toast.LENGTH_LONG
    ).show()

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
