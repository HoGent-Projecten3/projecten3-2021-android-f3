package com.example.faith.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MediumRemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(keys: MediumRemoteKey)

    @Query("SELECT * FROM remote_keys WHERE medium = :medium")
    suspend fun remoteKeyByPost(medium: String): MediumRemoteKey

    @Query("DELETE FROM remote_keys WHERE medium = :medium")
    suspend fun deleteBySubreddit(medium: String)
}