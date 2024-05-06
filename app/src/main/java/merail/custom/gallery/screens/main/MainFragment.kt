package merail.custom.gallery.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import merail.custom.gallery.R
import merail.custom.gallery.databinding.FragmentMainBinding
import merail.custom.gallery.main.Navigator
import merail.custom.gallery.screens.albumlist.AlbumListFragment
import merail.custom.gallery.screens.medialist.MediaListFragment
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