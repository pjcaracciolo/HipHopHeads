package com.hiphopheads

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hiphopheads.databinding.AdapterPostBinding
import com.hiphopheads.models.Posts

class MainAdapter(private val onItemClicked: (position: Int) -> Unit): RecyclerView.Adapter<MainViewHolder>() {
    private var posts = mutableListOf<Posts>()
    private var filteredPosts = mutableListOf<Posts>()

    fun setPostList(posts: List<Posts>) {
        this.posts = posts.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AdapterPostBinding.inflate(inflater, parent, false)
        return MainViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val post = posts[position]
        val postTitle = post.data?.title
        holder.binding.postTitle.text = sanitizeTitle(postTitle)
        holder.binding.mediaType.text = getMediaType(postTitle)
        val thumbnail = post.data?.secureMedia?.oembed?.thumbnailUrl
        Glide.with(holder.itemView.context).load(thumbnail).into(holder.binding.imageview)
    }

    override fun getItemCount(): Int {
        return posts.size
    }

    private fun sanitizeTitle(title: String?) : String? {
        return removeTag(title?.replace("&amp;", "&"));
    }

    private fun removeTag(title: String?) : String? {
        return title?.replace("\\[.*\\]".toRegex(), "")?.trim();
    }

    private fun getMediaType(title: String?) : String {
        var lowercaseTitle : String? = title?.lowercase()
        if (lowercaseTitle?.startsWith("[fresh]")!!) {
            return "Song"
        }
        if (lowercaseTitle.startsWith("[fresh album]")) {
            return "Album"
        }
        if (lowercaseTitle.startsWith("[fresh ep]")) {
            return "EP"
        }
        if (lowercaseTitle.startsWith("[fresh mixtape]")) {
            return "Mixtape"
        }
        return "Unknown"
    }

    fun showFavoritePosts(artists: Array<String>) {
        for (post in posts) {
            val title = post.data?.title
            if(containsFavoriteArtist(title!!, artists)) {
                filteredPosts.add(post)
            }
        }
        posts = filteredPosts
        println("POSTS: $posts")
        notifyDataSetChanged()
    }

    private fun containsFavoriteArtist(title: String, artists: Array<String>): Boolean {
        for (artist in artists) {
            if (title.contains(artist)) {
                return true
            }
        }
        return false
    }

    fun getFilteredPosts(): MutableList<Posts> {
        return filteredPosts
    }

}

class MainViewHolder(val binding: AdapterPostBinding, private val onItemClicked: (position: Int) -> Unit) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        binding.linksButton.setOnClickListener(this)
    }
    override fun onClick(view: View?) {
        val position = adapterPosition
        onItemClicked(position)
    }


}