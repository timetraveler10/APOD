package com.hussein.apod.presentation.main_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.hussein.apod.R
import com.hussein.apod.domain.model.Apod
import com.hussein.apod.ui.theme.CardOutlineColor
import com.hussein.apod.util.DateUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApodListItem(apod: Apod, onClick: (id: Int) -> Unit) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .wrapContentHeight(),
        onClick = { onClick(apod.id!!) },
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            0.7.dp,
            color = CardOutlineColor
        ), color = Color.White
    ) {

        Box(modifier = Modifier) {
            if (apod.isImage) {

                AsyncImage(
                    contentScale = ContentScale.Crop,
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(apod.hdUrl)
                        .crossfade(true)
                        .allowHardware(false)
                        .placeholder(R.drawable.ic_outline_error_outline_24)
                        .build(),
                    modifier = Modifier
                        .height(250.dp)
                        .fillMaxWidth(),
                    contentDescription = null,
                )

                Row(
                    modifier = Modifier
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color.White.copy(alpha = .4f)
                                )
                            )
                        )
                        .fillMaxWidth()
                        .height(180.dp)
                        .align(Alignment.BottomStart)
                        .padding(10.dp)
                ) {


                    MainBody(apod = apod)

                }

            } else {

                MainBody(apod, modifier = Modifier.padding(8.dp))
            }

        }

    }

}


private fun setFontColor(noPhoto: Boolean): Color {
    return if (noPhoto) Color.Black else Color.White
}

@Composable
fun MainBody(apod: Apod, modifier: Modifier = Modifier) {
    val noPhoto = !apod.isImage

    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Bottom
    ) {

        Text(
            text = apod.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2, color = setFontColor(noPhoto)
        )
        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = apod.explanation,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            overflow = TextOverflow.Ellipsis, lineHeight = 20.sp,
            maxLines = 5, color = setFontColor(noPhoto)
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = DateUtils.dateToString(apod.date),
            fontSize = 12.sp,
            color = setFontColor(noPhoto)
        )


    }
}