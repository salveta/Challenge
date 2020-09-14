package com.salvaperez.challenge.presentation.utils

import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.salvaperez.challenge.R

class ChallengeDialog(
    private val context: Context,
    private val title: String? = null,
    private val message: String,
    private val cancelable: Boolean = true,
    val onYesClicked: () -> Unit,
    val onNoClicked: () -> Unit
) {
    fun show() {
        val dialog = MaterialDialog(context)
            .cancelable(cancelable)
            .message(text = message)

        title?.let { dialog.title(text = it) }

        dialog.show {
            positiveButton(text = context.getString(R.string.ok_title)) { onYesClicked() }
            negativeButton(text = context.getString(R.string.no_title)) { onNoClicked() }
        }
    }
}