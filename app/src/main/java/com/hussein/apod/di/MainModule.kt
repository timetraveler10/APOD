package com.hussein.apod.di

import android.app.Application
import androidx.room.Room
import com.hussein.apod.data.local.ApodDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MainModule {

    @Provides
    @Singleton
    fun provideApodDatabase(context: Application): ApodDatabase {
        return Room.databaseBuilder(context, ApodDatabase::class.java, "apod_database.db").build()
    }

}