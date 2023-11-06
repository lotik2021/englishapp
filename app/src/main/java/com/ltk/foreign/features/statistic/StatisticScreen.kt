@file:OptIn(ExperimentalMaterial3Api::class)

package com.ltk.foreign.features.statistic

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ltk.foreign.R
import com.ltk.foreign.features.word.study.WordScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticScreen() {
    val viewModel = hiltViewModel<WordScreenViewModel>()

    var searchQuery by rememberSaveable { mutableStateOf("") }
    var isGridView by rememberSaveable { mutableStateOf(false) }

    val snackBarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },

        content = {
            Surface(
                modifier = Modifier.padding(it)
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    StatisticView(
                        contentDescription = stringResource(R.string.no_words_added),
                        message = stringResource(R.string.click_on_the_compose_button_to_add)
                    )
                }
            }
        }
    )
}

@Composable
fun StatisticView(contentDescription: String, message: String) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = R.drawable.ic_no_list),
            contentDescription = "null"
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = contentDescription,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(
                Font(R.font.poppins_medium)
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = message,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(Font(R.font.poppins_light))
        )
    }
}
