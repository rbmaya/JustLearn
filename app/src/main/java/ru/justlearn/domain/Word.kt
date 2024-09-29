package ru.justlearn.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Word(
    val id: String,
    val value: String,
    val phonetics: List<Phonetic>,
    val meanings: List<Meaning>,
): Parcelable