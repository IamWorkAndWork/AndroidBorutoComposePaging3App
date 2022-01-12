package com.example.myborutocomposepaging3app.presentation.screens.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.myborutocomposepaging3app.R
import com.example.myborutocomposepaging3app.ui.theme.topAppBarBackgroundColor


@Composable
fun HomeTopBar(
    onSearchClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.home_text_explorer),
                color = MaterialTheme.colors.topAppBarBackgroundColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_icon)
                )
            }
        }
    )
}

@Preview
@Composable
fun HomeTopBarPreview() {
    HomeTopBar {}
}