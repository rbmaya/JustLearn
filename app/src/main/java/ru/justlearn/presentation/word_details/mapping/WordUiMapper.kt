package ru.justlearn.presentation.word_details.mapping

import ru.justlearn.auxiliary.Mapper
import ru.justlearn.domain.Word

object WordUiMapper : Mapper<Word, UiWord>() {

    override fun map(source: Word): UiWord {
        return with(source) {
            UiWord(
                id = id,
                value = value,
                phonetics = phonetics,
                meanings = meanings.map {
                    UiMeaning(
                        partOfSpeech = it.partOfSpeech,
                        definitions = it.definitions,
                        synonyms = it.synonyms.joinToString(separator = ", "),
                        antonyms = it.antonyms.joinToString(separator = ", "),
                    )
                }
            )
        }
    }

}