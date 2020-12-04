package com.example.faith.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DoelDTODao {
    /*@Query("SELECT * FROM doelen")
    fun getAll(): LiveData<List<Doel>>*/

    /*@Query("SELECT * FROM doelen WHERE naam = :doelNaam")
    fun getOne(doelNaam: String): LiveData<Doel>*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(doelenDTO: List<DoelDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(doel: Doel)
}