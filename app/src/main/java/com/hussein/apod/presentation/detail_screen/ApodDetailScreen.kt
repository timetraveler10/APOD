package com.hussein.apod.presentation.detail_screen

import android.graphics.Color.parseColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.hussein.apod.R
import com.hussein.apod.presentation.MainViewModel
import com.hussein.apod.presentation.utils.ApodEvents
import com.ramcosta.composedestinations.annotation.Destination



@Composable
@Destination
fun ApodDetailScreen(mainViewModel: MainViewModel, id: Int) {

    mainViewModel.onEvent(ApodEvents.GetApod(id = id))

    val colors by mainViewModel.colors

    val fetchedApod = mainViewModel.fetchedApod


    var vibrant by remember { mutableStateOf("#000000") }
    val vibrantColor by animateColorAsState(
        targetValue = Color(
            parseColor(vibrant)
        )
    )
    var onVibrant by remember { mutableStateOf("#000000") }
    var mutedSwatch by remember { mutableStateOf("#000000") }
    var darkVibrant by remember { mutableStateOf("#000000") }
    var lightMutedSwatch by remember { mutableStateOf("#000000") }
    var onDarkVibrant by remember { mutableStateOf("#000000") }

    var loadingImage by remember { mutableStateOf(true) }

    val systemUiController = rememberSystemUiController()


    fetchedApod.value?.let {

        Column(modifier = Modifier.fillMaxSize()) {
            if (!(it.hdUrl.isNullOrBlank())) {

                AsyncImage(
                    contentScale = ContentScale.FillWidth,
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(it.hdUrl)
                        .crossfade(true)
                        .allowHardware(false)
                        .placeholder(R.drawable.ic_baseline_error_24)
                        .build(),
                    contentDescription = null,
                    onSuccess = {
                        mainViewModel.extractColorsFromBitmap(it.result.drawable.toBitmap())

                        darkVibrant = colors["darkVibrant"] ?: "#FFFFFF"
                        vibrant = colors["vibrant"] ?: "#FFFFFF"
                        mutedSwatch = colors["mutedSwatch"] ?: "#FFFFFF"
                        onDarkVibrant = colors["onDarkVibrant"] ?: "#FFFFFF"
                        onVibrant = colors["onVibrant"] ?: "#FFFFFF"
                        lightMutedSwatch = colors["lightMutedSwatch"] ?: "#FFFFFF"

                        systemUiController.setStatusBarColor(
                            color = Color(
                                parseColor(
                                    mutedSwatch
                                )
                            )
                        )
                        loadingImage = false
                    }, modifier = Modifier.placeholder(
                        visible = loadingImage,
                        color = Color.Gray,
                        // optional, defaults to RectangleShape
                        shape = RoundedCornerShape(4.dp),
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color.LightGray,
                        ),
                    ).fillMaxWidth()
                )


                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), color = vibrantColor

                ) {

                    Column {


                        Text(
                            modifier = Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            text = it.title,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 2,
                            color = Color(
                                parseColor(onVibrant)
                            )
                        )


                        Text(
                            text = it.explanation,
                            modifier = Modifier
                                .padding(
                                    horizontal = 8.dp
                                )
                                .verticalScroll(rememberScrollState()),
                            fontSize = 18.sp,
                            color = Color(parseColor(onVibrant))
                        )
                    }
                }

            } else {
                Column(modifier = Modifier.fillMaxSize().padding(horizontal = 8.dp , vertical = 4.dp)) {
                    Text(text = it.title, fontWeight = FontWeight.Medium, fontSize = 22.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = it.explanation,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.weight(1f), fontSize = 18.sp
                    )

                }
            }


        }
    }


}

