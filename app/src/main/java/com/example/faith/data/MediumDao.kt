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
    @Query("SELECT * FROM media WHERE mediumType != 4 ORDER BY naam")
    fun getMedia(): PagingSource<Int, Medium>
    @Query("SELECT * FROM media WHERE mediumId = :mediumId")
    fun getMedium(mediumId: Int): LiveData<Medium>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(media: List<Medium>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(medium: Medium)
    @Delete
    suspend fun deleteMedium(medium: Medium)
}
