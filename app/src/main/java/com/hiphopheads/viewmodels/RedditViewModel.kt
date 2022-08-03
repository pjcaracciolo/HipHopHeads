package com.hiphopheads.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hiphopheads.R
import com.hiphopheads.CommentsModel
import com.hiphopheads.Link
import com.hiphopheads.repositories.RedditRepository
import com.hiphopheads.models.Posts
import com.hiphopheads.models.RedditModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class RedditViewModel constructor(private val repository: RedditRepository) : ViewModel() {

    val movieList = MutableLiveData<List<RedditModel>>()
    val requestObject = MutableLiveData<RedditModel>()
    val childrenList = MutableLiveData<List<Posts>>()
    var commentsList = MutableLiveData<List<CommentsModel>>()
    val errorMessage = MutableLiveData<String>()

    fun getAllMovies() {
        val response = repository.getAllMovies()
        response.enqueue(object : Callback<List<RedditModel>> {
            override fun onResponse(call: Call<List<RedditModel>>, response: Response<List<RedditModel>>) {
                movieList.postValue(response.body())
            }
            override fun onFailure(call: Call<List<RedditModel>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getComments(permalink: String) {
        val response = repository.getComments(permalink.replace("/r/hiphopheads/", ""))
        response.enqueue(object : Callback<List<CommentsModel>> {
            override fun onResponse(call: Call<List<CommentsModel>>, response: Response<List<CommentsModel>>) {
                commentsList.postValue(response.body())
                println("BODY: " + response.body())
            }
            override fun onFailure(call: Call<List<CommentsModel>>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getRequestObject() {
        val response = repository.getRequestObject()
        response.enqueue(object : Callback<RedditModel> {
            override fun onResponse(call: Call<RedditModel>, response: Response<RedditModel>) {
                requestObject.postValue(response.body())
                childrenList.postValue(response.body()?.data?.children)
            }
            override fun onFailure(call: Call<RedditModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })

    }

    fun getFreshPosts() {
        val response = repository.getRequestObject()
        response.enqueue(object : Callback<RedditModel> {
            override fun onResponse(call: Call<RedditModel>, response: Response<RedditModel>) {
                requestObject.postValue(response.body())
                var freshPostsList = ArrayList<Posts>()
                for (child in response.body()?.data?.children!!) {
                    val title: String? = child.data?.title
                    if (isFreshPost(title)!!) {
                        freshPostsList.add(child)
                        setLinks(child)
                    }
                }
                childrenList.postValue(freshPostsList)
            }
            override fun onFailure(call: Call<RedditModel>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun isFreshPost(title: String?) : Boolean? {
        val lowercaseTitle = title?.lowercase()
        return (lowercaseTitle?.contains("[fresh]")!! || lowercaseTitle.contains("[fresh album]") || lowercaseTitle.contains("[fresh ep]") || lowercaseTitle.contains("[fresh mixtape]"))
    }

    fun setLinks(child : Posts) {
        val postText : String? = child.data?.selftext
        val regex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(postText)
        var link : String
        val postUrl = child.data?.url
        if (postUrl != null) {
            setLink(child, postUrl)
        }
        while (matcher.find()) {
            link = matcher.group()
            setLink(child, link)
        }
    }

    fun setLinks(comments: CommentsModel, child: Posts) {
        var body = ""
        for(comment in comments.data?.children!!) {
            body += comment.data.body + "\n"
        }
        val regex = "(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(body)
        var link : String
        while (matcher.find()) {
            link = matcher.group()
            setLink(child, link)
        }
    }

    private fun setLink(child: Posts, link: String) {
        val links = child.data?.links
        with (link) {
            when {
                contains("spotify.com") && links?.spotify == null -> child.data?.links?.spotify = Link("Spotify", link, R.color.spotify)
                contains("apple.com") && links?.apple == null -> child.data?.links?.apple = Link("Apple Music", link, R.color.apple)
                contains("amazon.com") && links?.amazon == null -> child.data?.links?.amazon = Link("Amazon Music", link, R.color.amazon)
                contains("tidal.com") && links?.tidal == null -> child.data?.links?.tidal = Link("Tidal", link, R.color.tidal)
                contains("deezer.com") && links?.deezer == null -> child.data?.links?.deezer = Link("Deezer", link, R.color.deezer)
                contains("music.youtube.com") && links?.youtubeMusic == null -> child.data?.links?.youtubeMusic = Link("YouTube Music", link, R.color.youtubeMusic)
                (contains("youtube.com") || contains("youtu.be")) && !contains("music.youtube.com") && links?.youtube == null -> child.data?.links?.youtube = Link("YouTube", link, R.color.youtube)
                contains("soundcloud.com") && links?.soundcloud == null -> child.data?.links?.soundcloud = Link("SoundCloud", link, R.color.soundcloud)
                contains("bandcamp.com") && links?.bandcamp == null -> child.data?.links?.bandcamp = Link("bandcamp", link, R.color.bandcamp)
            }
        }
    }

}