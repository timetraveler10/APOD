package com.hussein.apod.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.palette.graphics.Palette
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult

object PaletteGenerator {

    suspend fun convertImageToBitmap(imageUrl: String, context: Context): Bitmap? {

        val loader = ImageLoader(context)

        val request = ImageRequest.Builder(context).data(imageUrl).allowHardware(false).build()

        val imageResult = loader.execute(request)
        return if (imageResult is SuccessResult) {
            (imageResult.drawable as BitmapDrawable).bitmap
        } else null
    }

    fun extractColorFromBitmap(bitmap: Bitmap): Map<String, String> {
        return mapOf(
            "vibrant" to parseColorSwatch(Palette.from(bitmap).generate().vibrantSwatch)
            ,
            "mutedSwatch" to parseColorSwatch(Palette.from(bitmap).generate().mutedSwatch)
            ,
            "darkVibrantSwatch" to parseColorSwatch(Palette.from(bitmap).generate().darkVibrantSwatch)
            ,
            "darkMutedSwatch" to parseColorSwatch(Palette.from(bitmap).generate().darkMutedSwatch)
            ,
            "darkVibrant" to parseColorSwatch(Palette.from(bitmap).generate().darkVibrantSwatch)
            ,
            "lightMutedSwatch" to parseColorSwatch(Palette.from(bitmap).generate().lightMutedSwatch)
            ,
            "onVibrant" to parseBodyColor(
                Palette.from(bitmap).generate().vibrantSwatch?.bodyTextColor
            ) , "onDarkVibrant" to parseBodyColor(
                Palette.from(bitmap).generate().darkVibrantSwatch?.bodyTextColor
            )

        )
    }

    private fun parseBodyColor(color: Int?): String {
        return if (color != null){
            val parsedColor = Integer.toHexString(color)
            return "#$parsedColor"
        }else "#FFFFFF"
    }

    private fun parseColorSwatch(color: Palette.Swatch?): String {
        return if (color != null) {
            val parsedColor = Integer.toHexString(color.rgb)
            return "#$parsedColor"
        } else "#000000"
    }
}