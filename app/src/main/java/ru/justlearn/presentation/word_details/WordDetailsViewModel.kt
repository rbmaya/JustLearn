package ru.justlearn.presentation.word_details

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ru.justlearn.auxiliary.EventHandler
import ru.justlearn.domain.Word

@HiltViewModel(assistedFactory = WordDetailsViewModel.WordDetailsViewModelFactory::class)
class WordDetailsViewModel @AssistedInject constructor(
    @Assisted val word: Word
) : ViewModel(), EventHandler<WordDetailsEvent> {

    private val _screenState = MutableStateFlow(
        WordDetailsScreenState(word = word)
    )
    val screenState = _screenState.asStateFlow()

    override fun obtainEvent(event: WordDetailsEvent) {
        TODO("Not yet implemented")
    }

    @AssistedFactory
    interface WordDetailsViewModelFactory {
        fun create(word: Word): WordDetailsViewModel
    }
}