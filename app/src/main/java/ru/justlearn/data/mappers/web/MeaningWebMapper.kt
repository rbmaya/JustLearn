package ru.justlearn.data.mappers.web

import ru.justlearn.auxiliary.Mapper
import ru.justlearn.data.model.MeaningWebModel
import ru.justlearn.domain.Meaning

object MeaningWebMapper: Mapper<MeaningWebModel, Meaning>() {

    override fun map(source: MeaningWebModel): Meaning = with(source) {
        return Meaning(
            partOfSpeech =  partOfSpeech,
            definitions = DefinitionWebMapper.mapList(definitions),
            synonyms = synonyms,
            antonyms = antonyms,
        )
    }
}