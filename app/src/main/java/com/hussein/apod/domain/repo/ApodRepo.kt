package com.hussein.apod.domain.repo

import com.hussein.apod.domain.model.Apod
import com.hussein.apod.util.Resource
import kotlinx.coroutines.flow.Flow

interface ApodRepo {

    fun getAllData(): Flow<Resource<List<Apod>>>

    suspend fun getApodFromDb(id: Int): Apod

    suspend fun deleteApodFromDb(id: Int)

    suspend fun deleteAllApods()

//    suspend fun getApodsByDateRange(fromDate: String, toDate: String): Flow<Resource<List<Apod>>>

    fun getRandomApodsFromApi():Flow<Resource<List<Apod>>>

//    fun getApodFromApiByDate(date:String):Flow<Resource<Apod>>
}