package com.example.myborutocomposepaging3app.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberImagePainter
import com.example.myborutocomposepaging3app.R
import com.example.myborutocomposepaging3app.data.local.entity.HeroEntity
import com.example.myborutocomposepaging3app.navigation.Screen
import com.example.myborutocomposepaging3app.presentation.components.RatingWidget
import com.example.myborutocomposepaging3app.presentation.components.ShimmerEffect
import com.example.myborutocomposepaging3app.ui.theme.*
import com.example.myborutocomposepaging3app.util.Constant.BASE_URL

@Composable
fun ListContent(
    heroes: LazyPagingItems<HeroEntity>,
    navController: NavController
) {
    val result = handlePagingResult(heroes = heroes)

    if (result) {
        LazyColumn(
            contentPadding = PaddingValues(
                all = SMALL_PADDING
            ),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(
                items = heroes,
                key = { hero ->
                    hero.id
                }
            ) { hero ->
                hero?.let {
                    HeroItem(hero = it, navController = navController)
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(heroes: LazyPagingItems<HeroEntity>): Boolean {
    heroes.apply {

        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                EmptyScreen(error = error, heroes = heroes)
                false
            }
            heroes.itemCount < 1 -> {
                EmptyScreen()
                false
            }
            else -> {
                true
            }
        }
    }
}

@Composable
fun HeroItem(hero: HeroEntity, navController: NavController) {

    val painter = rememberImagePainter(data = "$BASE_URL${hero.image}") {
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_placeholder)
    }

    Box(
        modifier = Modifier
            .height(HERO_ITEM_HEIGHT)
            .clickable {
                navController.navigate(Screen.Details.passHeroId(heroId = hero.id))
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(size = LARGE_PADDING)) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = stringResource(id = R.string.hero_image),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Text(
                    text = hero.name,
                    color = MaterialTheme.colors.topAppBarBackgroundColor,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = hero.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        modifier = Modifier.padding(end = SMALL_PADDING),
                        rating = hero.rating
                    )
                    Text(
                        text = "${hero.rating}",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = ContentAlpha.medium)
                    )
                }
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
fun HeroItemPreview() {
    HeroItem(
        hero = HeroEntity(
            id = 1,
            name = "Sasuke",
            image = "",
            about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ",
            rating = 0.0,
            power = 100,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ), navController = rememberNavController()
    )
}

@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HeroItemDarkPreview() {
    HeroItem(
        hero = HeroEntity(
            id = 1,
            name = "Sasuke",
            image = "",
            about = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. ",
            rating = 0.0,
            power = 100,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ), navController = rememberNavController()
    )
}