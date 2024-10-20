package ru.justlearn.data.mappers.database

import ru.justlearn.data.saved.entities.AntonymEntity
import ru.justlearn.data.saved.entities.DefinitionEntity
import ru.justlearn.data.saved.entities.MeaningEntity
import ru.justlearn.data.saved.entities.PhoneticEntity
import ru.justlearn.data.saved.entities.SynonymEntity
import ru.justlearn.data.saved.entities.WordEntity
import ru.justlearn.domain.Word
import java.util.UUID

object WordEntityMapper {

    fun mapToEntity(word: Word): WordEntity = with(word) {
        WordEntity(
            value = value
        )
    }

    fun mapFromEntity(
        wordEntity: WordEntity,
        phoneticEntities: List<PhoneticEntity>,
        meaningEntities: List<MeaningEntity>,
        definitionsByMeanings: Map<Int, List<DefinitionEntity>>,
        synonymsByMeanings: Map<Int, List<SynonymEntity>>,
        antonymsByMeanings: Map<Int, List<AntonymEntity>>,
        ): Word = with(wordEntity) {
        val wordId = UUID.randomUUID().toString()
        Word(
            id = wordId,
            value = value,
            phonetics = phoneticEntities.map(PhoneticEntityMapper::mapFromEntity),
            meanings = meaningEntities.map { meaningEntity ->
                MeaningEntityMapper.mapFromEntity(
                    meaningEntity = meaningEntity,
                    definitionEntities = definitionsByMeanings[meaningEntity.id]!!,
                    synonymEntities = synonymsByMeanings[meaningEntity.id]!!,
                    antonymEntities = antonymsByMeanings[meaningEntity.id]!!,
                )
            }
        )
    }
}