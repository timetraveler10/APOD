package com.hussein.apod.presentation.utils

sealed class ApodEvents {
    object GenerateColors : ApodEvents()
    data class GetApod(val id: Int) : ApodEvents()
    object GetRandomApods : ApodEvents()
    data class GetApodByDateFromApi(val date:String)
}
