package com.example.faith.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
/**
 * @author Remi Mestdagh
 */
@Dao
interface MediumDao {
    @Query("SELECT * FROM media WHERE mediumType != 4 ORDER BY datum DESC")
    fun getMedia(): PagingSource<Int, Medium>


    @Query("SELECT * FROM media WHERE mediumType = 4 ORDER BY datum DESC")
    fun getDagboek(): PagingSource<Int, Medium>

    @Query("SELECT * FROM media WHERE mediumId = :mediumId")
    fun getMedium(mediumId: Int): LiveData<Medium>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(media: List<Medium>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(medium: Medium)
    @Query("DELETE FROM media WHERE mediumId = :id")
    suspend fun deleteMedium(id: Int)
}
