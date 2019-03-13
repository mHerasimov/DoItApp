package com.mikeherasimov.doitapp.ui.util

import java.util.*

fun Calendar.eraseTime() {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}

fun Calendar.eraseDate() {
    set(Calendar.YEAR, 0)
    set(Calendar.DATE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}