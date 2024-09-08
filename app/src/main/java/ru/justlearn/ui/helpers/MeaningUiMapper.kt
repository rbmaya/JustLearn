package ru.justlearn.ui.helpers

import ru.justlearn.auxiliary.Mapper
import ru.justlearn.domain.Meaning
import ru.justlearn.presentation.word_details.model.UiMeaning

object MeaningUiMapper : Mapper<Meaning, UiMeaning>() {

    override fun map(source: Meaning): UiMeaning {
        return with(source) {
            UiMeaning(
                partOfSpeech = partOfSpeech.name.first().lowercase(),
                definitions = definitions,
            )
        }
    }
}