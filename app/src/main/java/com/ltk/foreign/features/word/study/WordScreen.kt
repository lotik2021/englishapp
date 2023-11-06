package com.ltk.foreign.features.word.study

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ltk.foreign.data.model.Dict
import com.ltk.foreign.data.model.Word
import com.ltk.foreign.features.word.dict.DictScreenViewModel
import com.ltk.foreign.features.word.dict.WordCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordScreen() {
    val viewModel = hiltViewModel<WordScreenViewModel>()

    val snackBarHostState = remember { SnackbarHostState() }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var titleX by remember { mutableStateOf("") }
    var translateX by remember { mutableStateOf("") }
    titleX = viewModel.wordModel.observeAsState().value?.name ?: ""
    translateX = viewModel.wordModel.observeAsState().value?.translate ?: ""
    LaunchedEffect(Unit) {
        viewModel.loadWord()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Study words")
                },
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },

        content = {
            Surface(
                modifier = Modifier.padding(it)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BottomSheetShow(sheetState, scope)
                }
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    WordStudy(titleX, translateX, viewModel)
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetShow(
    sheetState: SheetState, scope: CoroutineScope
) {
    val viewModel = hiltViewModel<WordScreenViewModel>()

    val dictList by viewModel.listOfDict.collectAsState(
        initial = emptyList()
    )

    ClickableText(text = viewModel._dict.title) {
        scope.launch {
            sheetState.show()
        }
    }

    if (sheetState.isVisible) {
        ModalBottomSheet(
            sheetState = sheetState,
            onDismissRequest = {
                scope.launch {
                    sheetState.hide()
                }
            },
        ) {
            Row(horizontalArrangement = Arrangement.SpaceAround) {
                DictNamesContent(dictList, viewModel, sheetState, scope)
//                CenterAlignedTopAppBar(navigationIcon = {
//                    IconButton(onClick = {
//                        scope.launch {
//                            sheetState.hide()
//                        }
//                    }) {
//                        Icon(Icons.Rounded.Close, contentDescription = "Cancel")
//                    }
//                }, title = { Text("Content") }, actions = {
//                    IconButton(onClick = { }) {
//                        Icon(Icons.Rounded.Check, contentDescription = "Save")
//                    }
//                })
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DictNamesContent(
    dicts: List<Dict>,
    viewModel: WordScreenViewModel,
    sheetState: SheetState,
    scope: CoroutineScope
) {
    LazyColumn(
    ) {
        items(
            items = dicts
        ) { dictLoc ->
            DictCard(
                dict = dictLoc,
                viewModel = viewModel,
                sheetState = sheetState,
                scope = scope
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DictCard(
    dict: Dict,
    viewModel: WordScreenViewModel,
    sheetState: SheetState,
    scope: CoroutineScope
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
                .padding(12.dp)
                .background(Color.Transparent).clickable {
                    viewModel.loadNewDict(dict.id)
                    scope.launch {
                        sheetState.hide()
                    }
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = dict.title,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WordStudy(
    titleXY: String,
    translateXY: String,
    vm: WordScreenViewModel
) {
    AnimatedContent(
        targetState = "",
        transitionSpec = {
            scaleIn(animationSpec = tween(durationMillis = 1000)) with
                    ExitTransition.None
        },
        contentAlignment = Alignment.Center, label = ""
    ) { titleX ->
        Text(
            text = titleXY,
            textAlign = TextAlign.Center,
            fontSize = 36.sp
        )
    }

    Spacer(modifier = Modifier.height(height = 12.dp))

    AnimatedContent(
        targetState = "",
        transitionSpec = {
            scaleIn(animationSpec = tween(durationMillis = 500)) with
                    ExitTransition.None
        },
        contentAlignment = Alignment.Center, label = ""
    ) { translateX ->
        Text(
            text = translateXY,
            textAlign = TextAlign.Center,
            fontSize = 36.sp,
            lineHeight = 36.sp
        )
    }

    Spacer(modifier = Modifier.height(height = 12.dp))

    Button(onClick = {
        vm.loadNextWord()
    }) {
        Text(text = "Next word")
    }
}

@Composable
fun ClickableText(text: String, onClick: () -> Unit) {
    Text(
        modifier = Modifier
            .clickable(enabled = true) {
                onClick()
            },
        text = text,
        textAlign = TextAlign.Center,
        fontSize = 36.sp,
    )
}


