package me.rail.customgallery.medialist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import me.rail.customgallery.databinding.FragmentMediaListBinding
import me.rail.customgallery.media.MediaStorage

class MediaListFragment: Fragment() {
    private lateinit var binding: FragmentMediaListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMediaListBinding.inflate(inflater, container, false)

        binding.mediaList.layoutManager = GridLayoutManager(requireContext(), 3)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mediaList.adapter = MediaAdapter(MediaStorage.getImages())
    }
}