package com.hiphopheads.repositories

import com.hiphopheads.RetrofitService

class RedditRepository constructor(private val retrofitService: RetrofitService) {
    fun getAllMovies() = retrofitService.getAllMovies()
    fun getRequestObject() = retrofitService.getRequestObject()
    fun getComments(permalink: String) = retrofitService.getComments(permalink)
}