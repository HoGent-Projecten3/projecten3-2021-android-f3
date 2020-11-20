package com.example.faith.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
/**
 * @author Remi Mestdagh
 */
@Dao
interface MediumDao {
    @Query("SELECT * FROM media ORDER BY naam")
    fun getMedia(): LiveData<List<Medium>>
    @Query("SELECT * FROM media WHERE id = :mediumId")
    fun getMedium(mediumId: Int): LiveData<Medium>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(media: List<Medium>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(medium: Medium)
    @Query("DELETE FROM media WHERE id = :mediumId")
    suspend fun deleteMedium(mediumId: Int)
}
