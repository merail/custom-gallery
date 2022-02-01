package me.rail.customgallery.screens.albumlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import me.rail.customgallery.databinding.FragmentAlbumListBinding
import me.rail.customgallery.media.MediaStorage

class AlbumListFragment: Fragment() {
    private lateinit var binding: FragmentAlbumListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAlbumListBinding.inflate(inflater, container, false)

        binding.mediaList.layoutManager = GridLayoutManager(requireContext(), 3)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mediaList.adapter = AlbumAdapter(MediaStorage.getAlbums())
    }
}