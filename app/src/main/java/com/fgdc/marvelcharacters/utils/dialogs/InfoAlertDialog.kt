package com.fgdc.marvelcharacters.utils.dialogs

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.fgdc.marvelcharacters.databinding.InfoAlertDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoAlertDialog(context: Context) : Fragment() {

    private var cancelable = false
    private var isBackgroundTransparent = true
    private var dialog: AlertDialog? = null

    fun create(): AlertDialog {
        dialog = builder
            .setCancelable(cancelable)
            .create()

        if (isBackgroundTransparent) {
            dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }

        return dialog!!
    }

    fun onCancelListener(func: () -> Unit): AlertDialog.Builder? =
        builder.setOnCancelListener {
            func()
        }

    val binding = InfoAlertDialogBinding.inflate(LayoutInflater.from(context))

    val builder: AlertDialog.Builder =
        AlertDialog.Builder(context).setView(binding.root)

    fun setTitle(text: String) {
        with(binding.tvAlertInfoTitle) {
            this.text = text
        }
    }

    fun setText(text: String) =
        with(binding.tvAlertInfoMessage) {
            this.text = text
        }

    fun setButtonText(text: String) =
        with(binding.btnAlertInfoAccept) {
            this.text = text
        }

    fun btnAccept(clickListener: (() -> Unit)? = null) =
        with(binding.btnAlertInfoAccept) {
            setClickListenerToDialogIcon(clickListener)
        }

    private fun View.setClickListenerToDialogIcon(clickListener: (() -> Unit)?) =
        setOnClickListener {
            clickListener?.invoke()
            dialog?.dismiss()
        }
}
