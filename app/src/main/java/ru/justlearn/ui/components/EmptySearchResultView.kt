package ru.justlearn.ui.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.justlearn.R
import ru.justlearn.ui.theme.JustLearnTheme
import ru.justlearn.ui.theme.Typography

@Composable
fun EmptySearchResultView(
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int = R.string.empty_result_defailt_title,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Text(
            style = Typography.titleMedium,
            text = stringResource(id = titleRes),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun EmptySearchResultViewPreview() {
    JustLearnTheme {
        EmptySearchResultView()
    }
}