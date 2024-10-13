package ru.justlearn.presentation.word_details

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ChipColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.justlearn.R
import ru.justlearn.data.search.MockWordsDataSource
import ru.justlearn.domain.Definition
import ru.justlearn.domain.Phonetic
import ru.justlearn.presentation.word_details.mapping.UiMeaning
import ru.justlearn.presentation.word_details.mapping.WordUiMapper
import ru.justlearn.ui.helpers.ifNotBlank
import ru.justlearn.ui.theme.JustLearnTheme
import ru.justlearn.ui.theme.Typography

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun WordDetailsView(
    state: WordDetailsScreenState,
    onAudioClick: (String) -> Unit,
) {
    val word by remember {
        mutableStateOf(WordUiMapper.map(state.word))
    }
    val coroutineScope = rememberCoroutineScope()

    val scrollState = rememberScrollState()
    var floatingButtonIsVisible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(24.dp)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        floatingButtonIsVisible = coordinates.positionInWindow().y < 0
                    },
                text = word.value,
                style = Typography.headlineMedium
            )
            if (word.phonetics.isNotEmpty()) {
                Spacer(modifier = Modifier.height(12.dp))
                FlowRow(modifier = Modifier.fillMaxWidth()) {
                    word.phonetics.forEach { phonetic ->
                        PhoneticChip(phonetic = phonetic, onClick = onAudioClick)
                        Spacer(modifier = Modifier.width(8.dp))
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
            word.meanings.forEach { meaning ->
                MeaningCard(word = word.value, meaning = meaning)
                Spacer(modifier = Modifier.height(24.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        if (floatingButtonIsVisible) {
            SmallFloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(24.dp),
                onClick = {
                    coroutineScope.launch {
                        scrollState.animateScrollTo(0)
                    }
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowUp,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun PhoneticChip(phonetic: Phonetic, onClick: (String) -> Unit) {
    AssistChip(
        shape = RoundedCornerShape(32.dp),
        border = null,
        label = {
            Text(
                modifier = Modifier.padding(8.dp),
                text = phonetic.phonetic,
                style = Typography.titleMedium,
                fontStyle = FontStyle.Italic
            )
        },
        colors = ChipColors(
            containerColor = MaterialTheme.colorScheme.primary,
            labelColor = MaterialTheme.colorScheme.onPrimary,
            leadingIconContentColor = MaterialTheme.colorScheme.onPrimary,
            trailingIconContentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            disabledLabelColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledLeadingIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            disabledTrailingIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        leadingIcon = {
            if (!phonetic.audioUrl.isNullOrBlank()) {
                Image(
                    painter = painterResource(id = R.drawable.word_details_screen_sound_ic),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onPrimary)
                )
            }
        },
        enabled = !phonetic.audioUrl.isNullOrBlank(),
        onClick = {
            phonetic.audioUrl?.let { onClick(it) }
        },
    )
}

@Composable
fun MeaningCard(word: String, meaning: UiMeaning) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = word,
                style = Typography.titleLarge
            )
            Text(
                modifier = Modifier.padding(start = 12.dp),
                text = meaning.partOfSpeech,
                style = Typography.titleMedium,
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        meaning.definitions.forEachIndexed { index, definition ->
            DefinitionItem(definition = definition)
            if (index != meaning.definitions.lastIndex) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = 12.dp))
            }
        }
        meaning.synonyms.ifNotBlank { synonyms ->
            RelatedWordsCard(
                groupNameRes = R.string.word_details_screen_synonyms,
                words = synonyms
            )
        }
        meaning.antonyms.ifNotBlank { antonyms ->
            RelatedWordsCard(
                groupNameRes = R.string.word_details_screen_antonyms,
                words = antonyms
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun DefinitionItem(definition: Definition) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            text = definition.definition,
            style = Typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        definition.example.ifNotBlank {
            Text(
                fontStyle = FontStyle.Italic,
                text = "• ${definition.example}"
            )
        }
    }
}

@Composable
fun RelatedWordsCard(@StringRes groupNameRes: Int, words: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal,
                        fontSize = Typography.bodySmall.fontSize,
                    )
                ) {
                    append(stringResource(groupNameRes))
                }

                withStyle(
                    SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = Typography.bodySmall.fontSize,
                    )
                ) {
                    append(words)
                }
            }
        )
    }
}

@Preview
@Composable
fun WordInfoViewPreview() {
    JustLearnTheme(darkTheme = false) {
        WordDetailsView(
            state = WordDetailsScreenState(
                word = MockWordsDataSource.MOCK_WORD
            ),
            onAudioClick = {},
        )
    }
}