package com.fgdc.marvelcharacters.utils.helpers

import android.content.Context
import com.fgdc.marvelcharacters.utils.extensions.networkInfo
import javax.inject.Inject

interface NetworkHandler {
    val isConnected: Boolean
}

class NetworkHandlerImpl
@Inject constructor(private val context: Context) : NetworkHandler {
    override val isConnected get() = context.networkInfo
}
