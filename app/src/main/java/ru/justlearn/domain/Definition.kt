package ru.justlearn.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Definition(
    val definition: String,
    val example: String,
): Parcelable