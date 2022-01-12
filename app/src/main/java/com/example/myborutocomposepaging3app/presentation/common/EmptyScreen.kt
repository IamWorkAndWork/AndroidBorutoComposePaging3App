package com.example.myborutocomposepaging3app.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.myborutocomposepaging3app.R
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.ui.theme.DarkGray
import com.example.myborutocomposepaging3app.ui.theme.LightGray
import com.example.myborutocomposepaging3app.ui.theme.NETWORK_ERROR_ICON_HEIGHT
import com.example.myborutocomposepaging3app.ui.theme.SMALL_PADDING
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import java.net.ConnectException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(
    error: LoadState.Error? = null,
    heroes: LazyPagingItems<HeroEntity>? = null
) {

    val word = stringResource(id = R.string.common_text_find_your_favorite_hero)

    var message by remember {
        mutableStateOf(word)
    }

    var icon by remember {
        mutableStateOf(R.drawable.ic_search_document)
    }

    if (error != null) {
        message = parseErrorMessage(error = error)
        icon = R.drawable.ic_network_error
    }

    var startAnimation by remember {
        mutableStateOf(false)
    }

    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) ContentAlpha.disabled else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
    }

    EmptyContent(
        alphaAnim = alphaAnim,
        icon = icon,
        message = message,
        error = error,
        heroes = heroes
    )
}

@Composable
fun EmptyContent(
    alphaAnim: Float,
    icon: Int,
    message: String,
    error: LoadState.Error? = null,
    heroes: LazyPagingItems<HeroEntity>? = null
) {
    var isRefreshing by remember {
        mutableStateOf(false)
    }

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing = isRefreshing),
        onRefresh = {
            isRefreshing = true
            heroes?.refresh()
            isRefreshing = false
        },
        swipeEnabled = error != null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier
                    .size(NETWORK_ERROR_ICON_HEIGHT)
                    .alpha(alpha = alphaAnim),
                painter = painterResource(id = icon),
                contentDescription = stringResource(id = R.string.network_error_icon),
                tint = if (isSystemInDarkTheme()) LightGray else DarkGray
            )
            Text(
                text = message,
                modifier = Modifier
                    .padding(top = SMALL_PADDING)
                    .alpha(alpha = alphaAnim),
                color = if (isSystemInDarkTheme()) LightGray else DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
        }
    }

}

fun parseErrorMessage(error: LoadState.Error): String {
    return when (error.error) {
        is SocketTimeoutException -> {
            "Server Unavailable."
        }
        is ConnectException -> {
            "Internet Unavailable."
        }
        else -> {
            "Unknow Error"
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyScreenPreview() {
    EmptyContent(
        alphaAnim = ContentAlpha.disabled,
        icon = R.drawable.ic_network_error,
        message = "Internet Unavailable.",
    )
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun EmptyScreenDarkPreview() {
    EmptyContent(
        alphaAnim = ContentAlpha.disabled,
        icon = R.drawable.ic_network_error,
        message = "Internet Unavailable."
    )
}