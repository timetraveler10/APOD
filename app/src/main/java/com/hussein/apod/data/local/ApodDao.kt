package com.hussein.apod.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ApodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApod(apodEntity: ApodEntity)

    @Query("DELETE FROM ApodEntity")
    suspend fun deleteAllApods()

    @Query("SELECT * FROM ApodEntity ORDER BY id DESC")
    suspend fun getAllAPods(): List<ApodEntity>

    @Query("SELECT * FROM ApodEntity WHERE id = :id")
    suspend fun getApod(id: Int): ApodEntity

    @Query("SELECT date FROM ApodEntity ORDER BY id DESC LIMIT 1")
    suspend fun getLastRecordDate(): Long?

    @Query("DELETE  FROM ApodEntity WHERE id= :id")
    fun deleteApod(id: Int)


}