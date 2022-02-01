package me.rail.customgallery.screens.viewpager

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import me.rail.customgallery.databinding.FragmentViewPagerBinding

class ViewPagerFragment: Fragment() {
    private lateinit var binding: FragmentViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        binding.root.isClickable = true

        binding.pager.adapter = ImageAdapter(requireActivity())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.takeIf { it.containsKey(ARG_POSITION) }?.apply {
            binding.pager.post {
                binding.pager.setCurrentItem(getInt(ARG_POSITION), false)
            }
        }
    }

    companion object {
        private const val ARG_POSITION = "position"

        @JvmStatic
        fun newInstance(position: Int) =
            ViewPagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, position)
                }
            }
    }
}