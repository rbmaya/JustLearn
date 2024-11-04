package ru.justlearn.data.mappers.database

import ru.justlearn.data.saved.entities.AntonymEntity
import ru.justlearn.data.saved.entities.DefinitionEntity
import ru.justlearn.data.saved.entities.MeaningEntity
import ru.justlearn.data.saved.entities.SynonymEntity
import ru.justlearn.domain.Meaning

object MeaningEntityMapper {

    fun mapToEntity(
        wordId: Long,
        meaning: Meaning
    ): MeaningEntity = with(meaning) {
        MeaningEntity(
            wordId = wordId,
            partOfSpeech = partOfSpeech,
        )
    }

    fun mapFromEntity(
        meaningEntity: MeaningEntity,
        definitionEntities: List<DefinitionEntity>,
        synonymEntities: List<SynonymEntity>,
        antonymEntities: List<AntonymEntity>,
    ): Meaning = with(meaningEntity) {
        Meaning(
            partOfSpeech = partOfSpeech,
            definitions = definitionEntities.map(
                DefinitionEntityMapper::mapFromEntity
            ),
            synonyms = synonymEntities.map { it.value },
            antonyms = antonymEntities.map { it.value }
        )
    }
}