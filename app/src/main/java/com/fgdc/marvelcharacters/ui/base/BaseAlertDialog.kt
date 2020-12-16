package com.fgdc.marvelcharacters.ui.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AlertDialog

abstract class BaseAlertDialog {

    abstract val builder: AlertDialog.Builder

    open var cancelable = false
    open var isBackgroundTransparenet = true

    open var dialog: AlertDialog? = null

    open fun create(): AlertDialog {
        dialog = builder
            .setCancelable(cancelable)
            .create()

        if (isBackgroundTransparenet) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        return dialog!!
    }

    open fun onCancelListener(func: () -> Unit): AlertDialog.Builder? =
        builder.setOnCancelListener {
            func()
        }
}
