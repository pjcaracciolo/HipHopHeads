package com.hiphopheads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.hiphopheads.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener{

    companion object {
        var postsList: List<Posts>? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener(this)
        loadFragment(AllReleasesFragment.newInstance())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment
        when (item.itemId) {
            R.id.allReleases -> {
                fragment = AllReleasesFragment.newInstance()
                loadFragment(fragment)
                return true
            }
            R.id.myReleases -> {
                fragment = AllReleasesFragment.newInstance()
                val args = Bundle()
                args.putStringArray("artists", arrayOf("Styles P", "RXKNephew"))
                fragment.setArguments(args)
                loadFragment(fragment)
                return true

            }
            R.id.savedReleases -> {
                //fragment = SearchFragment()
                //loadFragment(fragment)
                return true

            }
            else -> return false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

}