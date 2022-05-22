package com.hussein.apod.data.mappers

import com.hussein.apod.data.local.ApodEntity
import com.hussein.apod.data.remote.dto.ApodDto
import com.hussein.apod.domain.model.Apod
import com.hussein.apod.util.DateUtils

fun ApodDto.toApodEntity(): ApodEntity {
    return ApodEntity(
        date = DateUtils.dateToLong(date = DateUtils.stringToDate(date)),
        explanation = explanation ?: "",
        url = url ?: "",
        hdUrl = hdurl ?: "",
        title = title ?: "",
        media_type = mediaType,
        favorite = null
    )
}

fun ApodEntity.toApod(): Apod {
    return Apod(
        date = DateUtils.longToDate(date),
        explanation = explanation,
        hdUrl = hdUrl,
        url = url,
        title = title,
        id = id,
        isImage = media_type == "image",
        favorite = favorite == 1
    )
}

fun ApodDto.toApod(): Apod {
    return Apod(
        date = DateUtils.stringToDate(date),
        explanation = explanation ?: "",
        url = url ?: "",
        hdUrl = hdurl ?: "",
        title = title ?: "",
        isImage = mediaType == "image",
        favorite = false
    )
}

