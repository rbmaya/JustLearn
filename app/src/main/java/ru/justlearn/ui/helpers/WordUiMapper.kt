package ru.justlearn.ui.helpers

import ru.justlearn.auxiliary.Mapper
import ru.justlearn.domain.Word
import ru.justlearn.presentation.word_details.model.UiWord

object WordUiMapper : Mapper<Word, UiWord>() {

    override fun map(source: Word): UiWord {
        return with(source) {
            UiWord(
                id = id,
                value = value,
                phonetic = phonetic,
                audioUrl = audioUrl,
                meanings = MeaningUiMapper.mapList(meanings),
                synonyms = synonyms.joinToString(separator = ", "),
                antonyms = antonyms.joinToString(separator = ", "),
            )
        }
    }

}