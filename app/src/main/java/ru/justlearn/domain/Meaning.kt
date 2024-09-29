package ru.justlearn.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>,
    val synonyms: List<String>,
    val antonyms: List<String>,
): Parcelable