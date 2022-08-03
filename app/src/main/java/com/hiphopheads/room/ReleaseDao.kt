package com.hiphopheads.room

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ReleaseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(release: Release)

    @Delete
    suspend fun delete(release: Release)

    @Query("Select * from releaseTable order by id ASC")
    fun getAllReleases(): LiveData<List<Release>>

    @Update
    suspend fun update(release: Release)
}