package ru.justlearn.presentation.saved

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.justlearn.auxiliary.EventHandler
import ru.justlearn.domain.saved.SavedWordsRepository
import javax.inject.Inject

@HiltViewModel
class SavedWordListViewModel @Inject constructor(
    private val savedWordsRepository: SavedWordsRepository,
) : ViewModel(), EventHandler<SavedWordListEvent> {

    private val _screenState = MutableStateFlow(
        SavedWordsListScreenState(
            wordList = emptyList(),
            isInitialLoading = true,
        )
    )
    val screenState = _screenState.asStateFlow()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _screenState.update { it.copy(isInitialLoading = false) }
        Log.e("SavedWordListViewModel", "Error during loading saved words", throwable)
    }

    override fun obtainEvent(event: SavedWordListEvent) {
        when (event) {
            SavedWordListEvent.OpenScreen -> loadSavedWordList()
        }
    }

    private fun loadSavedWordList() {
        viewModelScope.launch(exceptionHandler) {
            val words = savedWordsRepository.getSavedWords()
            _screenState.update {
                it.copy(
                    wordList = words,
                    isInitialLoading = false,
                )
            }
        }
    }
}