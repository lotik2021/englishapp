package com.ltk.foreign.features.common.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ltk.foreign.R
import com.ltk.foreign.features.settings.SettingsScreen
import com.ltk.foreign.features.statistic.StatisticScreen
import com.ltk.foreign.features.word.dict.DictScreen
import com.ltk.foreign.features.word.study.WordScreen
import kotlinx.coroutines.launch

@Composable
fun ForeignNav(navController: NavHostController = rememberNavController()) {
    val bottomNavItem = getBottomNavItems()
    val backStackEntry = navController.currentBackStackEntryAsState()

    val listState: LazyListState = rememberLazyListState()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(
                backStackEntry,
                bottomNavItem,
                navController,
                listState
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = ForeignScreens.Word.name,
            modifier = Modifier.padding(it),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(700)
                )
            }
        ) {
            composable(
                route = ForeignScreens.Word.name
            ) {
                WordScreen(
                )
            }

//            composable(
//                route = ForeignScreens.Statistics.name
//            ) {
//                StatisticScreen()
//            }

            composable(
                route = ForeignScreens.Words.name
            ) {
                DictScreen()
            }

            composable(
                route = ForeignScreens.Settings.name
            ) {
                SettingsScreen()
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    backStackEntry: State<NavBackStackEntry?>,
    bottomNavItem: List<BottomNavItem>,
    navController: NavHostController,
    lazyListState: LazyListState,
) {
    val scope = rememberCoroutineScope()

    NavigationBar(modifier = Modifier.height(75.dp)) {
        bottomNavItem.forEach { item ->
            NavigationBarItem(
                alwaysShowLabel = true,
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = item.name,
                        tint = if (backStackEntry.value?.destination?.route == item.route) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.secondary
                        }
                    )
                },
                label = {
                    Text(
                        text = item.name,
                        color = if (backStackEntry.value?.destination?.route == item.route) {
                            MaterialTheme.colorScheme.onSurface
                        } else {
                            MaterialTheme.colorScheme.secondary
                        },
                        fontWeight = if (backStackEntry.value?.destination?.route == item.route) {
                            FontWeight.Bold
                        } else {
                            FontWeight.Normal
                        }
                    )
                },
                selected = backStackEntry.value?.destination?.route == item.route,
                onClick = {
                    if (item.name == getBottomNavItems().first().name) {
                        scope.launch {
                            lazyListState.animateScrollToItem(0)
                        }
                    }
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }
    }

}

fun getBottomNavItems(): List<BottomNavItem> {
    return listOf(
        BottomNavItem(
            name = "Word",
            route = ForeignScreens.Word.name,
            icon = R.drawable.ic_word_study
        ),
//        BottomNavItem(
//            name = "Statistics",
//            route = ForeignScreens.Statistics.name,
//            icon = R.drawable.ic_line_chart
//        ),
        BottomNavItem(
            name = "Words",
            route = ForeignScreens.Words.name,
            icon = R.drawable.ic_words
        ),
        BottomNavItem(
            name = "Settings",
            route = ForeignScreens.Settings.name,
            icon = R.drawable.ic_setting
        )
    )
}
