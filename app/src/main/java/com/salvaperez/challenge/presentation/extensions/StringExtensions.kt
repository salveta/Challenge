package com.salvaperez.challenge.presentation.extensions

import java.text.DecimalFormat

fun String.formatCredit(): String {
    val format = DecimalFormat("###,###.##")

    return format.format(this.toDouble())
}