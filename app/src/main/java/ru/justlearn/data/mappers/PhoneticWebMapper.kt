package ru.justlearn.data.mappers

import ru.justlearn.auxiliary.Mapper
import ru.justlearn.data.model.PhoneticWebModel
import ru.justlearn.domain.Phonetic

object PhoneticWebMapper: Mapper<PhoneticWebModel, Phonetic?>() {

    override fun map(source: PhoneticWebModel): Phonetic? = with(source) {
        text ?: return@with null

        Phonetic(
            phonetic = text,
            audioUrl = audio
        )
    }
}