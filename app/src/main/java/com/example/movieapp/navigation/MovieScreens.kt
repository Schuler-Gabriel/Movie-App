package com.example.movieapp.navigation

import androidx.compose.ui.input.key.Key.Companion.Home

enum class MovieScreens {
    HomeScreen,
    DetailsScreen;
    companion object {
        fun fromRoute(route: String?): MovieScreens
        = when (route?.substringBefore("/")){
            HomeScreen.name -> HomeScreen
            DetailsScreen.name -> DetailsScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognize")
        }
    }
}