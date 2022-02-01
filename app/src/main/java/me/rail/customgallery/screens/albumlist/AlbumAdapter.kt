package me.rail.customgallery.screens.albumlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import me.rail.customgallery.databinding.ItemMediaBinding
import me.rail.customgallery.models.Image

class AlbumAdapter(private val albums: LinkedHashMap<String, ArrayList<Image>>):
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(val binding: ItemMediaBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemMediaBinding.inflate(inflater, viewGroup, false)

        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val thumbnail = ArrayList(albums.values)[position][0]
        val name = ArrayList(albums.keys)[position]

        holder.binding.image.load(thumbnail.uri) {
            crossfade(true)
        }
        holder.binding.name.text = name
    }

    override fun getItemCount(): Int {
        return albums.size
    }
}