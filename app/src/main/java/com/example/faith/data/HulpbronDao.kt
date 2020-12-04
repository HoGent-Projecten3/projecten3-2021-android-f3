package com.example.faith.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * The Data Access Object for the Hulpbron class.
 */
@Dao
interface HulpbronDao {
    @Query("SELECT * FROM hulpbronnen ORDER BY titel")
    fun getAll(): LiveData<List<Hulpbron>>

    @Query("SELECT * FROM hulpbronnen WHERE id = :hulpbronId")
    fun getOne(hulpbronId: Int): LiveData<Hulpbron>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(hulpbronnen: List<Hulpbron>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(hulpbron: Hulpbron)
}