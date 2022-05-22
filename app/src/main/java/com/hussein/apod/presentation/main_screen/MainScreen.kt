package com.hussein.apod.presentation.main_screen

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hussein.apod.presentation.MainViewModel
import com.hussein.apod.presentation.destinations.ApodDetailScreenDestination
import com.hussein.apod.presentation.destinations.RandomApodScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Destination(start = true)
@RootNavGraph(true)
fun MainScreen(viewModel: MainViewModel, navigator: DestinationsNavigator) {

    val state = viewModel.state

    rememberSystemUiController().setStatusBarColor(
        color = MaterialTheme.colorScheme.primary
    )


    state.data?.let {

        val decayAnimationSpec = rememberSplineBasedDecay<Float>()
        val scrollBehavior = remember(decayAnimationSpec) {
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(decayAnimationSpec)
        }
        Scaffold(

            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                LargeTopAppBar(
                    title = {
                        if (scrollBehavior.scrollFraction in 0f..(.1f)) Text(
                            "Astronomy Picture Of The Day",
                            fontSize = 30.sp
                        ) else Text("APOD", fontSize = 25.sp)
                    },
                    actions = {
                        IconButton(onClick = { navigator.navigate(RandomApodScreenDestination) }) {
                            Icon(
                                imageVector = Icons.Filled.Explore,
                                contentDescription = null
                            )
                        }


                    },
                    scrollBehavior = scrollBehavior
                )
            },
            content = { innerPadding ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(
                        top = innerPadding.calculateTopPadding(),
                        bottom = innerPadding.calculateBottomPadding(), start = 8.dp, end = 8.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Text(text = "Today's APOD" , modifier = Modifier.padding(horizontal = 8.dp))
                    }
                    item {
                        ApodListItem(it.first()) {
                            navigator.navigate(ApodDetailScreenDestination(id = it))
                        }
                    }
                    item {
                        Text(text = "Other APODS" , modifier = Modifier.padding(horizontal = 8.dp))
                    }
                    items(it.drop(1)) {
                        ApodListItem(it) {
                            navigator.navigate(ApodDetailScreenDestination(id = it))
                        }
                    }
                }
            },
        )

    }


    Box(modifier = Modifier.fillMaxSize()) {
        if (state.isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
            )
        }
        if (state.msg != null) {
            Text(
                text = state.msg, modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center)
            )
        }
    }

}
