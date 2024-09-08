package ru.justlearn.presentation.word_details

import androidx.annotation.StringRes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
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
import ru.justlearn.presentation.word_details.model.UiMeaning
import ru.justlearn.ui.helpers.WordUiMapper
import ru.justlearn.ui.theme.JustLearnTheme
import ru.justlearn.ui.theme.Typography

@Composable
fun WordDetailsView(
    state: WordDetailsScreenState,
) = with(state) {
    val coroutineScope = rememberCoroutineScope()

    val screenHeightPx = with(LocalDensity.current) {
        LocalConfiguration.current.screenHeightDp.dp.toPx()
    }
    val scrollState = rememberScrollState()
    var floatingButtonIsVisible by remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = word.value,
                style = Typography.headlineMedium
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .onGloballyPositioned { coordinates ->
                        val itemBounds = coordinates.boundsInWindow()
                        floatingButtonIsVisible =
                            !itemBounds.overlaps(Rect(0f, 0f, itemBounds.width, screenHeightPx))
                    }
            ) {
                Button(onClick = { /*TODO*/ }) {
                    Image(
                        painter = painterResource(id = R.drawable.word_details_screen_sound_ic),
                        contentDescription = null
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp),
                    text = word.phonetic,
                    style = Typography.headlineSmall,
                    fontStyle = FontStyle.Italic
                )
            }
            word.meanings.forEach { meaning ->
                MeaningCard(word = word.value, meaning = meaning)
                Spacer(modifier = Modifier.height(12.dp))
            }
            RelatedWordsCard(
                groupNameRes = R.string.word_details_screen_synonyms,
                words = word.synonyms
            )
            Spacer(modifier = Modifier.height(12.dp))
            RelatedWordsCard(
                groupNameRes = R.string.word_details_screen_antonyms,
                words = word.antonyms
            )
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
fun MeaningCard(word: String, meaning: UiMeaning) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(24.dp),
                text = word,
                style = Typography.titleLarge
            )
            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = meaning.partOfSpeech,
                style = Typography.titleLarge,
                color = MaterialTheme.colorScheme.secondary
            )
        }
        meaning.definitions.forEach { definition ->
            DefinitionItem(definition = definition)
        }
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
        Text(text = "• ${definition.example}")
    }
}

@Composable
fun RelatedWordsCard(@StringRes groupNameRes: Int, words: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
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
                word = WordUiMapper.map(MockWordsDataSource.MOCK_WORD)
            )
        )
    }
}