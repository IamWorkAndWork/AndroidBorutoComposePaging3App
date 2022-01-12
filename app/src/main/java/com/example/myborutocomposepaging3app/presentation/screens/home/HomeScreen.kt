package com.example.myborutocomposepaging3app.presentation.screens.home

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.myborutocomposepaging3app.navigation.Screen
import com.example.myborutocomposepaging3app.presentation.common.ListContent
import com.example.myborutocomposepaging3app.ui.theme.statusBarColor
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = MaterialTheme.colors.statusBarColor
    )

    Scaffold(
        topBar = {
            HomeTopBar(
                onSearchClicked = {
                    navController.navigate(Screen.Search.route)
                }
            )
        },
        content = {
            ListContent(heroes = allHeroes, navController = navController)
        }
    )

}