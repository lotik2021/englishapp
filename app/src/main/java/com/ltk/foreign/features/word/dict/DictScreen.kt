@file:OptIn(ExperimentalMaterial3Api::class)

package com.ltk.foreign.features.word.dict

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ltk.foreign.R
import com.ltk.foreign.data.model.Word

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DictScreen(
) {
    val viewModel = hiltViewModel<DictScreenViewModel>()

    val wordList by viewModel.listOfWord.collectAsState(
        initial = emptyList()
    )

    val snackBarHostState = remember { SnackbarHostState() }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        topBar = {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                query = searchQuery,
                onQueryChange = { search ->
                    searchQuery = search
                },
                onSearch = {},
                active = false,
                onActiveChange = {},
                placeholder = { Text(stringResource(R.string.search_your_words)) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable { searchQuery = "" },
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
            ) {}

        },
        content = { padding ->
            DictContent(
                padding = padding,
                words = wordList,
                searchQuery = searchQuery
            )
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DictContent(
    padding: PaddingValues,
    words: List<Word>,
    searchQuery: String
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
    ) {
        items(
            words.filter { word ->
                word.name.contains(searchQuery, true)
            }
        ) { wordLoc ->
            WordCard(
                word = wordLoc
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun WordCard(
    word: Word
) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            )
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = word.name,
                    fontSize = 25.sp
                )
                Text(
                    text = word.translate,
                    fontSize = 25.sp
                )
            }
        }
    }
}