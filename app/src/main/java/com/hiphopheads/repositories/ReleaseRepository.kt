package com.hiphopheads.repositories

import androidx.lifecycle.LiveData
import com.hiphopheads.room.Release
import com.hiphopheads.room.ReleaseDao

class ReleaseRepository(private val releaseDao: ReleaseDao) {

    val allReleases: LiveData<List<Release>> = releaseDao.getAllReleases()

    suspend fun insert(release: Release) {
        releaseDao.insert(release)
    }

    suspend fun delete(release: Release) {
        releaseDao.delete(release)
    }

    suspend fun update(release: Release) {
        releaseDao.update(release)
    }
}