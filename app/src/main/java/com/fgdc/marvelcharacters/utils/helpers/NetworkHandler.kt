package com.fgdc.marvelcharacters.utils.helpers

import android.content.Context
import com.fgdc.marvelcharacters.utils.extensions.networkInfo
import javax.inject.Inject

class NetworkHandler
@Inject constructor(private val context: Context) {
    val isConnected get() = context.networkInfo?.isConnected
}
