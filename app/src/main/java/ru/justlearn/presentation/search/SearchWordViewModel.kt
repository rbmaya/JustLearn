package ru.justlearn.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import ru.justlearn.auxiliary.EventHandler
import ru.justlearn.domain.search.WordsRepository
import javax.inject.Inject

@HiltViewModel
class SearchWordViewModel @Inject constructor(
    private val wordsRepository: WordsRepository,
) : ViewModel(), EventHandler<SearchWordEvent> {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _screenState.update { it.copy(isSearching = false) }
        Log.e("SearchWordViewModel", "Error during search", throwable)
    }

    private val _screenState = MutableStateFlow(SearchWordState())
    val screenState = _screenState.asStateFlow()

    private var searchJob: Job? = null

    override fun obtainEvent(event: SearchWordEvent) {
        produceState(event)
    }

    private fun produceState(event: SearchWordEvent) {
        when (event) {
            is SearchWordEvent.QueryChanged -> {
                searchWord(event.query)
            }

            is SearchWordEvent.ClearQuery -> {
                clearQuery()
            }
        }
    }

    private fun searchWord(query: String) {
        _screenState.update { it.copy(query = query) }

        searchJob?.cancel()
        searchJob = viewModelScope.launch(exceptionHandler) {
            delay(SEARCH_DEBOUNCE_MS)
            _screenState.update { it.copy(isSearching = true) }

            val wordsByQuery = wordsRepository.getWords(query.trim())

            _screenState.update {
                it.copy(
                    isSearching = false,
                    items = wordsByQuery,
                    showPlaceholder = wordsByQuery.isEmpty() && query.isNotBlank()
                )
            }
        }
    }

    private fun clearQuery() {
        _screenState.update {
            it.copy(
                query = "",
                items = emptyList(),
                showPlaceholder = false,
            )
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 1000L
    }
}