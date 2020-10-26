package com.example.faith.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MediumDao {
    @Query("SELECT * FROM media ORDER BY naam")
    fun getMedia(): LiveData<List<Medium>>
    @Query("SELECT * FROM media WHERE id = :mediumId")
    fun getMedium(mediumId: Int): LiveData<Medium>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(media: List<Medium>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(medium: Medium)
}