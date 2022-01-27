package com.asd.template_inventar.navigation

sealed class Screen(val route: String) {
    object ListScreen: Screen(route="list")
    object AddScreen: Screen(route="add")
    object TopScreen: Screen(route="top")
}