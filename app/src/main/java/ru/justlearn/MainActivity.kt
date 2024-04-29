package ru.justlearn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.justlearn.presentation.search.SearchWordScreen
import ru.justlearn.presentation.search.SearchWordViewModel
import ru.justlearn.ui.theme.JustLearnTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JustLearnTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    SearchWordScreen(hiltViewModel<SearchWordViewModel>())
                }
            }
        }
    }
}


