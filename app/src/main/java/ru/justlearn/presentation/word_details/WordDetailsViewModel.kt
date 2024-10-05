package ru.justlearn.presentation.word_details

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("WordDetailsViewModel", "Error during playing audio", throwable)
    }

    override fun obtainEvent(event: WordDetailsEvent) {
        produceState(event)
    }

    private fun produceState(event: WordDetailsEvent) {
        when (event) {
            is WordDetailsEvent.PlayAudio -> playAudio(event.url)
        }
    }

    private fun playAudio(url: String) {
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                MediaPlayer().apply {
                    setAudioAttributes(
                        AudioAttributes.Builder()
                            .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .build()
                    )
                    setDataSource(url)
                    prepare()
                    start()
                }
            }
        }
    }

    @AssistedFactory
    interface WordDetailsViewModelFactory {
        fun create(word: Word): WordDetailsViewModel
    }
}