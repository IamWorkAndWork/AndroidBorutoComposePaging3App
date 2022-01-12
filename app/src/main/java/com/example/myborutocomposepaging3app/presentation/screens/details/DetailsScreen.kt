package com.example.myborutocomposepaging3app.presentation.screens.details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myborutocomposepaging3app.util.Constant.BASE_URL
import com.example.myborutocomposepaging3app.util.PalatteGenerator.convertImaeUrlToBitmap
import com.example.myborutocomposepaging3app.util.PalatteGenerator.extractColorsFromBitmap
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@Composable
fun DetailsScreen(
    navController: NavHostController,
    detailsViewModel: DetailsViewModel = hiltViewModel()
) {

    val selectedHero by detailsViewModel.selectedHero.collectAsState()
    val colorPalatte by detailsViewModel.colorPalatte

    if (colorPalatte.isNotEmpty()) {
        DetailsContent(
            navController = navController,
            selectedHero = selectedHero,
            colors = colorPalatte
        )
    } else {
        detailsViewModel.generateColorPalatte()
    }

    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        detailsViewModel.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.GenerateColorPalatte -> {
                    val bitmap = convertImaeUrlToBitmap(
                        imageUrl = "${BASE_URL}${selectedHero?.image}",
                        context = context
                    )
                    if (bitmap != null) {
                        detailsViewModel.setColorPalatte(
                            colors = extractColorsFromBitmap(
                                bitmap = bitmap
                            )
                        )
                    }
                }
            }
        }
    }

}

