package ru.justlearn.data.mappers.database

import ru.justlearn.data.saved.entities.PhoneticEntity
import ru.justlearn.domain.Phonetic

object PhoneticEntityMapper {

    fun mapToEntity(wordId: Int, phonetic: Phonetic): PhoneticEntity = with(phonetic) {
        PhoneticEntity(
            wordId = wordId,
            phonetic = this.phonetic,
            audioUrl = audioUrl
        )
    }

    fun mapFromEntity(entity: PhoneticEntity): Phonetic = with(entity) {
        Phonetic(
            phonetic = this.phonetic,
            audioUrl = audioUrl,
        )
    }

}