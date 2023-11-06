package com.ltk.foreign.features.common.navigation

sealed class ForeignScreens(val name: String) {

    object Word : ForeignScreens("word")
    object Statistics : ForeignScreens("statistics")
    object Words : ForeignScreens("words")
    object Settings : ForeignScreens("setting")
}
