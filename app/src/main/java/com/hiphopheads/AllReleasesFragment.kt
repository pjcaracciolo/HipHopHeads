package com.hiphopheads

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hiphopheads.R
import com.example.hiphopheads.databinding.FragmentAllReleasesBinding
import com.hiphopheads.models.Links
import com.hiphopheads.models.Posts
import com.hiphopheads.repositories.RedditRepository
import com.hiphopheads.room.Release
import com.hiphopheads.viewmodels.RedditViewModel
import com.hiphopheads.viewmodels.RedditViewModelFactory
import com.hiphopheads.viewmodels.ReleaseViewModel

class AllReleasesFragment : Fragment() {

    private lateinit var binding: FragmentAllReleasesBinding
    private lateinit var viewModel: RedditViewModel
    lateinit var releaseViewModel: ReleaseViewModel
    private val retrofitService = RetrofitService.getInstance()
    private lateinit var adapter : MainAdapter
    private lateinit var postsList: List<Posts>
    private var artists: Array<String>? = null

    companion object {
        @JvmStatic
        fun newInstance() = AllReleasesFragment()
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        val args = arguments
        if(args != null) {
            artists = args.getStringArray("artists")
        }
        viewModel = ViewModelProvider(this, RedditViewModelFactory(RedditRepository(retrofitService))).get(
            RedditViewModel::class.java)
        releaseViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            ReleaseViewModel::class.java)
        adapter = MainAdapter{position -> onListItemClick(position)}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllReleasesBinding.inflate(layoutInflater, container, false)
        binding.recyclerview.adapter = adapter
        viewModel.childrenList.observe(viewLifecycleOwner, Observer {
            adapter.setPostList(it)
            if(artists != null) {
                adapter.showFavoritePosts(artists!!)
                postsList = adapter.getFilteredPosts()
            } else {
                postsList = it
            }
            MainActivity.postsList = postsList
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            println("ERROR: $it")
        })
        if(MainActivity.postsList != null) {
            adapter.setPostList(MainActivity.postsList!!)
            if(artists != null) {
                adapter.showFavoritePosts(artists!!)
                postsList = adapter.getFilteredPosts()
            } else {
                postsList = MainActivity.postsList!!
            }
            postsList = MainActivity.postsList!!
        } else {
            viewModel.getFreshPosts()
        }
        return binding.root
    }


    private fun onListItemClick(position: Int) {
        val current = postsList[position]
        releaseViewModel.addRelease(Release(current.data?.title!!, current.data?.url!!, current.data?.permalink!!, current.data?.thumbnail!!, 667, "", "", "", "", "", "", "", "", ""/*current.data?.links?.spotify?.link!!, current.data?.links?.apple?.link!!, current.data?.links?.amazon?.link!!, current.data?.links?.tidal?.link!!, current.data?.links?.deezer?.link!!, current.data?.links?.youtubeMusic?.link!!, current.data?.links?.youtube?.link!!, current.data?.links?.soundcloud?.link!!, current.data?.links?.bandcamp?.link!!*/))
        viewModel.commentsList.observe(viewLifecycleOwner, Observer {
            viewModel.setLinks(it[1], current)
            displayLinksDialog(current.data?.links?.getLinks())
            viewModel.commentsList = MutableLiveData<List<CommentsModel>>()
        })
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            displayLinksDialog(current.data?.links?.getLinks())
        })
        viewModel.getComments(current.data?.permalink!!)
    }

    private fun Links.getLinks() : ArrayList<Link?> {
        val linksList = arrayListOf(spotify, apple, amazon, tidal, deezer, youtubeMusic, youtube, soundcloud)
        val validLinksList = ArrayList<Link?>()
        for (link in linksList) {
            if(link != null) {
                validLinksList.add(link)
            }
        }
        return validLinksList
    }

    private var linksList: ArrayList<Link?>? = null

    private fun displayLinksDialog(linksList: ArrayList<Link?>?) {
        this.linksList = linksList
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it.requireContext())
        }
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.links_dialog,null, false)
        builder.setView(view)
        val dialog: AlertDialog = builder.create()
        val linksAdapter = LinksAdapter{position -> onLinkClick(position)}
        val linksRecyclerView: RecyclerView = view.findViewById(R.id.linksRecyclerView)
        linksRecyclerView.layoutManager = LinearLayoutManager(view.context)
        linksRecyclerView.layoutParams = LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        linksRecyclerView.adapter = linksAdapter
        if (linksList != null) {
            linksAdapter.setLinksList(linksList)
        }
        val closeButton: Button = view.findViewById(R.id.linksCloseDialog)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun onLinkClick(position: Int) {
        val link = linksList?.get(position)?.link
        val linkIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        startActivity(linkIntent)
    }
}