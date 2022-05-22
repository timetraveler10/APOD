package com.hussein.apod.presentation.main_screen

data class ApodState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val msg: String? = null
)