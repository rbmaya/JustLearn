package ru.justlearn.data.model

import com.google.gson.annotations.SerializedName

class PhoneticWebModel(
    @SerializedName("text")
    val text: String?,
    @SerializedName("audio")
    val audio: String?,
)