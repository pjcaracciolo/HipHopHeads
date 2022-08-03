package com.hiphopheads

import com.hiphopheads.models.RedditModel
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService {
    @GET("hot.json?limit=10")
    fun getAllMovies() : Call<List<RedditModel>>

    @GET("hot.json?limit=100")
    fun getRequestObject(): Call<RedditModel>

    @GET("{permalink}.json")
    fun getComments(@Path(value = "permalink", encoded = true) permalink: String): Call<List<CommentsModel>>

    companion object {
        var retrofitService: RetrofitService? = null

        fun getInstance() : RetrofitService {
            return getInstance("https://www.reddit.com/r/hiphopheads/")
        }

        fun getInstance(base: String) : RetrofitService {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(base)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitService::class.java)
            }
            return retrofitService!!
        }
    }
}