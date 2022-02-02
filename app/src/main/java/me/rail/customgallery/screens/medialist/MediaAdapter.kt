package me.rail.customgallery.screens.medialist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.rail.customgallery.databinding.ItemMediaBinding
import me.rail.customgallery.models.Image
import me.rail.customgallery.models.Media
import me.rail.customgallery.models.Video

class MediaAdapter(
    private val medias: ArrayList<Media>,
    private val onImageClick: ((Int) -> Unit)? = null,
    private val onVideoClick: ((Int) -> Unit)? = null
):
    RecyclerView.Adapter<MediaAdapter.ImageViewHolder>() {

    class ImageViewHolder(val binding: ItemMediaBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemMediaBinding.inflate(inflater, viewGroup, false)

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = medias[position]

        holder.binding.image.load(item.uri) {
            crossfade(true)
        }
        holder.binding.name.text = item.name

        holder.binding.image.setOnClickListener {
            var correctPosition = position
            if (item is Image) {
                for (i in 0..position) {
                    if (medias[i] is Video) {
                        correctPosition--
                    }
                }
                onImageClick?.invoke(correctPosition)
            } else {
                for (i in 0..position) {
                    if (medias[i] is Image) {
                        correctPosition--
                    }
                }
                onVideoClick?.invoke(correctPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return medias.size
    }
}