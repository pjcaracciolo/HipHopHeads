package com.hiphopheads.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hiphopheads.repositories.RedditRepository
import java.lang.IllegalArgumentException

class RedditViewModelFactory constructor(private val repository: RedditRepository): ViewModelProvider.Factory {
    override fun <T: ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(RedditViewModel::class.java)) {
            RedditViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}