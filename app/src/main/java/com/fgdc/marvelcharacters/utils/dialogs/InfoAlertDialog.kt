package com.fgdc.marvelcharacters.utils.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.fgdc.marvelcharacters.databinding.InfoAlertDialogBinding
import com.fgdc.marvelcharacters.ui.base.BaseAlertDialog

class InfoAlertDialog(context: Context) : BaseAlertDialog() {

    val binding = InfoAlertDialogBinding.inflate(LayoutInflater.from(context))

    override val builder: AlertDialog.Builder =
        AlertDialog.Builder(context).setView(binding.root)

    fun setTitle(text: String) {
        with(binding.tvAlertInfoMessage) {
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
