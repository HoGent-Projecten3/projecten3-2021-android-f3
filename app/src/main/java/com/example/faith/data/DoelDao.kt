package com.example.faith.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DoelDao {
    @Query("SELECT * FROM doelen ORDER BY inhoud")
    fun getAll(): LiveData<List<Doel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(doelen: List<Doel>)
}