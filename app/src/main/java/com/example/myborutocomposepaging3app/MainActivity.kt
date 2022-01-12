package com.example.myborutocomposepaging3app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myborutocomposepaging3app.navigation.SetupNavGraph
import com.example.myborutocomposepaging3app.ui.theme.MyBorutoComposePaging3AppTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterialApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyBorutoComposePaging3AppTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}
