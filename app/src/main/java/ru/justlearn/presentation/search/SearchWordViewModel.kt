package ru.justlearn.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.justlearn.auxiliary.EventHandler
import ru.justlearn.domain.search.WordsRepository
import javax.inject.Inject

@HiltViewModel
class SearchWordViewModel @Inject constructor(
    private val wordsRepository: WordsRepository,
) : ViewModel(), EventHandler<SearchWordEvent> {

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
        }
    }

    private fun searchWord(query: String) {
        _screenState.update { it.copy(query = query, isSearching = true) }

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(SEARCH_DEBOUNCE_MS)

            val wordsByQuery = wordsRepository.getWords(query)
            _screenState.update { it.copy(isSearching = false, items = wordsByQuery) }
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_MS = 3000L
    }
}