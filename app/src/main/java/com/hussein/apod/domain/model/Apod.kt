package com.hussein.apod.domain.model

import java.util.*

data class Apod(
    val date: Date,
    val explanation: String,
    val hdUrl: String?,
    val title: String,
    val url: String? ,
    val id:Int? = null ,
    val isImage:Boolean ,
    val favorite:Boolean
)
