package com.salvaperez.challenge.presentation.extensions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.salvaperez.challenge.presentation.utils.ChallengeDialog

fun Activity.open(cls: Class<*>, extras: Bundle? = null) {
    val intent = Intent(this, cls)
    extras?.let { intent.putExtras(it) }
    startActivity(intent)
}

fun Activity.showDialog(
    title: String? = null,
    message: String,
    cancelable: Boolean = true,
    onYesClicked: () -> Unit,
    onNoClicked: () -> Unit
) {
    ChallengeDialog(this, title, message, cancelable, onYesClicked, onNoClicked).show()
}