package ru.justlearn.presentation.word_details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun WordDetailsScreen(
    viewModel: WordDetailsViewModel,
    onBackClicked: () -> Unit
) {
    val state by viewModel.screenState.collectAsState()

    WordDetailsView(
        state = state,
        onAudioClick = { url ->
            viewModel.obtainEvent(WordDetailsEvent.PlayAudio(url))
        },
        onSaveClick = {
            viewModel.obtainEvent(WordDetailsEvent.SaveOrUnsaveWord)
        },
        onBackClick = { onBackClicked() }
    )

    LaunchedEffect(true) {
        viewModel.obtainEvent(WordDetailsEvent.OpenScreen)
    }
}