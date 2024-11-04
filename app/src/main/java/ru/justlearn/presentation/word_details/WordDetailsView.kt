package ru.justlearn.presentation.word_details

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.justlearn.R
import ru.justlearn.data.search.MockWordsDataSource
import ru.justlearn.domain.Definition
import ru.justlearn.domain.Phonetic
import ru.justlearn.presentation.word_details.mapping.UiMeaning
import ru.justlearn.presentation.word_details.mapping.UiWord
import ru.justlearn.presentation.word_details.mapping.WordUiMapper
import ru.justlearn.ui.helpers.ifNotBlank
import ru.justlearn.ui.theme.JustLearnTheme
import ru.justlearn.ui.theme.Typography

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun WordDetailsView(
    state: WordDetailsScreenState,
    onAudioClick: (url: String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    val word by remember {
        mutableStateOf(WordUiMapper.map(state.word))
    }
    val coroutineScope = rememberCoroutineScope()

    val scrollAppBarBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val scrollContentState = rememberLazyListState()
    var floatingButtonIsVisible by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollAppBarBehavior.nestedScrollConnection),
        topBar = {
            AppBar(
                word = word,
                initialSaveStateProgress = state.initialSaveStateProgress,
                wordIsSaved = state.isSaved,
                scrollAppBarBehavior = scrollAppBarBehavior,
                onAudioClick = onAudioClick,
                onSaveClick = onSaveClick,
                onBackClick = onBackClick,
            )
        },
        floatingActionButton = {
            if (floatingButtonIsVisible) {
                FloatingActionButton(
                    onClick = {
                        coroutineScope.launch {
                            scrollAppBarBehavior.state.heightOffset = 0f
                            scrollContentState.animateScrollToItem(0)
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
    ) { innerPadding ->
        LazyColumn(
            state = scrollContentState,
            modifier = Modifier
                .padding(
                    start = 24.dp + innerPadding.calculateStartPadding(LayoutDirection.Ltr),
                    top = 24.dp + innerPadding.calculateTopPadding(),
                    end = 24.dp + innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                    bottom = 24.dp + innerPadding.calculateBottomPadding()
                )
        ) {
            itemsIndexed(word.meanings) { index, meaning ->
                MeaningCard(word = word.value, meaning = meaning)
                val offsetSize = if (index == word.meanings.lastIndex) 48.dp else 24.dp
                Spacer(modifier = Modifier.height(offsetSize))
            }
        }
    }

    LaunchedEffect(scrollContentState) {
        snapshotFlow { scrollContentState.canScrollBackward }
            .collect { canScrollBackward ->
                floatingButtonIsVisible = canScrollBackward
            }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AppBar(
    word: UiWord,
    initialSaveStateProgress: Boolean,
    wordIsSaved: Boolean,
    scrollAppBarBehavior: TopAppBarScrollBehavior,
    onAudioClick: (String) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    LargeTopAppBar(
        title = {
            Column {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = word.value,
                    style = Typography.headlineMedium
                )
                val appbarExpandedFraction = 1 - scrollAppBarBehavior.state.collapsedFraction
                if (word.phonetics.isNotEmpty() && appbarExpandedFraction > 0.5f) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .alpha(appbarExpandedFraction)
                    ) {
                        word.phonetics.forEach { phonetic ->
                            PhoneticChip(phonetic = phonetic, onClick = onAudioClick)
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        },
        navigationIcon = {
            IconButton(
                onClick = { onBackClick() },
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        actions = {
            if (!initialSaveStateProgress) {
                IconButton(
                    onClick = {
                        onSaveClick()
                    },
                ) {
                    Icon(
                        modifier = Modifier.size(30.dp),
                        imageVector = if (wordIsSaved) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Filled.FavoriteBorder
                        },
                        contentDescription = null
                    )
                }
            }
        },
        scrollBehavior = scrollAppBarBehavior
    )
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
fun MeaningCard(
    word: String,
    meaning: UiMeaning
) {
    Card(modifier = Modifier.fillMaxWidth()) {
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
                word = MockWordsDataSource.MOCK_WORD,
                isSaved = true,
                initialSaveStateProgress = false
            ),
            onAudioClick = {},
            onSaveClick = {},
            onBackClick = {}
        )
    }
}