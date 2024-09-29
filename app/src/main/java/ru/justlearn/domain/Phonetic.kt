package ru.justlearn.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Phonetic(
    val phonetic: String,
    val audioUrl: String?
): Parcelable