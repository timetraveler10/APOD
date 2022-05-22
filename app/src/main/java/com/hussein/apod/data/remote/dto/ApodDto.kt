package com.hussein.apod.data.remote.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApodDto(
    @SerialName("copyright")
    val copyright: String? = null,
    @SerialName("date")
    val date: String,
    @SerialName("explanation")
    val explanation: String? = null,
    @SerialName("hdurl")
    val hdurl: String? = null,
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("service_version")
    val serviceVersion: String? = null,
    @SerialName("title")
    val title: String? = null,
    @SerialName("url")
    val url: String? = null
)