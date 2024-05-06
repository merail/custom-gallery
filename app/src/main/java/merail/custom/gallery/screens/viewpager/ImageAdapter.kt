package merail.custom.gallery.screens.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import merail.custom.gallery.media.MediaStorage

class ImageAdapter(fragment: FragmentActivity, private val albumName: String?):
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = MediaStorage.getImageCount(albumName)

    override fun createFragment(position: Int): Fragment {

        return ImageFragment.newInstance(
            MediaStorage.getImageByPosition(position, albumName).uri.toString(),
        )
    }
}