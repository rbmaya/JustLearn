package ru.justlearn.data.mappers

import ru.justlearn.auxiliary.Mapper
import ru.justlearn.data.model.WordWebModel
import ru.justlearn.domain.Word
import java.util.UUID

object WordWebMapper: Mapper<WordWebModel, Word>() {

    override fun map(source: WordWebModel): Word = with(source) {
        Word(
            id = UUID.randomUUID().toString(),
            value = value,
            phonetics = PhoneticWebMapper.mapList(phonetics).filterNotNull(),
            meanings = MeaningWebMapper.mapList(meanings),
        )
    }
}