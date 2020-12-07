package com.example.faith.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * @author Jef Seys
 */
@Dao
interface BerichtDao {
    @Query("SELECT * FROM berichten")
    fun getBerichten(): LiveData<List<Bericht>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(bericht: Bericht)

    @Query("SELECT * FROM berichten ORDER BY datum")
    fun getAll(): PagingSource<Int, Bericht>
}
