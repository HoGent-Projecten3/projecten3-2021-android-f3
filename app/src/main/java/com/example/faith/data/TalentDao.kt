package com.example.faith.data

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
/**
 * @author Arne De Schrijver
 */
@Dao
interface TalentDao {

    @Query("SELECT * FROM Items WHERE id = :talentId")
    fun getTalent(talentId: Int): LiveData<Talent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(talenten: List<Talent>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOne(talent: Talent)
    @Query("DELETE FROM Items WHERE id = :talentId")
    suspend fun deleteTalent(talentId: Int)
    @Query("SELECT * FROM Items")
    fun getAll(): PagingSource<Int, Talent>
}