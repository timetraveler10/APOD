package com.hussein.apod.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ApodEntity(
    @PrimaryKey
    val id: Int? = null,
    val hdUrl: String,
    val title: String,
    val explanation: String,
    val url: String,
    val date: Long,
    val media_type: String?,
    val favorite: Int?

)
