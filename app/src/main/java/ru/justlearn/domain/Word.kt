package ru.justlearn.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Word(
    val id: String,
    val value: String,
    val phonetic: String,
    val audioUrl: String?,
    val meanings: List<Meaning>,
    val synonyms: List<String>,
    val antonyms: List<String>
): Parcelable