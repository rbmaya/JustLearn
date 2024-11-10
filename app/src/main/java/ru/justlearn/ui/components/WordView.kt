package ru.justlearn.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import ru.justlearn.domain.Word
import ru.justlearn.ui.theme.Typography

@Composable
fun WordView(word: Word, onWordClick: (Word) -> Unit) {
    Text(
        text = AnnotatedString(text = word.value),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onWordClick(word)
            }
            .padding(12.dp),
        style = Typography.titleMedium,
    )
}