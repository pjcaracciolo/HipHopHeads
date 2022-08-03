package com.hiphopheads

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.hiphopheads.R
import com.hiphopheads.viewmodels.ReleaseViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SavedReleasesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SavedReleasesFragment : Fragment() {

    lateinit var releaseViewModel: ReleaseViewModel

    companion object {
        @JvmStatic
        fun newInstance() = SavedReleasesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        releaseViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)).get(
            ReleaseViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        releaseViewModel.allReleases.observe(viewLifecycleOwner) {
            for (release in it) {
                println(release.title)
            }
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_saved_releases, container, false)
    }


}