package com.hiphopheads.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.hiphopheads.room.Release
import com.hiphopheads.room.ReleaseDatabase
import com.hiphopheads.repositories.ReleaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReleaseViewModel (application: Application) : AndroidViewModel(application){

    val allReleases: LiveData<List<Release>>
    val repository: ReleaseRepository

    init {
        val dao = ReleaseDatabase.getDatabase(application).getReleaseDao()
        repository = ReleaseRepository(dao)
        allReleases = repository.allReleases
    }

    fun deleteRelease(release: Release) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(release)
    }

    fun updateRelease(release: Release) = viewModelScope.launch (Dispatchers.IO) {
        repository.update(release)
    }

    fun addRelease(release: Release) = viewModelScope.launch (Dispatchers.IO) {
        repository.insert(release)
    }
}