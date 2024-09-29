package ru.justlearn.ui.helpers

inline fun String?.ifNotBlank(block: (String) -> Unit) {
    if (this.isNullOrBlank()) return
    block.invoke(this)
}