package com.fgdc.marvelcharacters.utils.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T : Any, L : LiveData<T>> LifecycleOwner.observe(liveData: L, body: (T?) -> Unit) =
    liveData.observe(this, Observer(body))

fun <L : LiveData<Throwable>> LifecycleOwner.failure(liveData: L, body: (Throwable?) -> Unit) =
    liveData.observe(this, Observer(body))
