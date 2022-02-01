package me.rail.customgallery.medialist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.rail.customgallery.databinding.ItemImageBinding
import me.rail.customgallery.models.Image

class MediaAdapter(private val images: ArrayList<Image>):
    RecyclerView.Adapter<MediaAdapter.ImageViewHolder>() {

    class ImageViewHolder(val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemImageBinding.inflate(inflater, viewGroup, false)

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = images[position]

        holder.binding.image.load(item.uri) {
            crossfade(true)
        }
        holder.binding.name.text = item.name
    }

    override fun getItemCount(): Int {
        return images.size
    }
}