package com.ltk.foreign.features.common.navigation

import androidx.annotation.DrawableRes

data class BottomNavItem(
    val name: String,
    val route: String,
    @DrawableRes val icon: Int,
)
