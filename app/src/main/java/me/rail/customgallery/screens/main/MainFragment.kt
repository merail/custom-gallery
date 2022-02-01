package me.rail.customgallery.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import me.rail.customgallery.R
import me.rail.customgallery.databinding.FragmentMainBinding
import me.rail.customgallery.main.Navigator
import me.rail.customgallery.screens.albumlist.AlbumListFragment
import me.rail.customgallery.screens.medialist.MediaListFragment
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment: Fragment() {
    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        setupBottomNavigationView()

        return binding.root
    }

    private fun setupBottomNavigationView() {
        binding.navigation.selectedItemId = R.id.mediaPage
        navigator.replaceFragment(R.id.fragmentContainer, MediaListFragment())
        binding.navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.mediaPage -> {
                    navigator.replaceFragment(R.id.fragmentContainer, MediaListFragment())
                }
                R.id.albumsPage -> {
                    navigator.replaceFragment(R.id.fragmentContainer, AlbumListFragment())
                }
                else -> {
                }
            }

            return@setOnItemSelectedListener true
        }
        binding.navigation.setOnItemReselectedListener {

        }
    }
}