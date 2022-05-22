package com.hussein.apod.data.remote

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KtorClientFactory@Inject constructor() {
    fun build(): HttpClient {
        return HttpClient(Android) {
             install(JsonFeature) {
                serializer = KotlinxSerializer(
                    kotlinx.serialization.json.Json {
                        ignoreUnknownKeys = true // if the server sends extra fields, ignore them
                    }
                )

            }

            install(HttpTimeout) {
                requestTimeoutMillis = 30000
                connectTimeoutMillis = 30000
            }

            install(Logging) {
                level = LogLevel.ALL
            }
        }
    }
}