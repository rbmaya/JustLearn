package ru.justlearn.data.model

import com.google.gson.annotations.SerializedName

class WordWebModel(
    @SerializedName("word")
    val value: String,
    @SerializedName("phonetics")
    val phonetics: List<PhoneticWebModel>,
    @SerializedName("meanings")
    val meanings: List<MeaningWebModel>,
)