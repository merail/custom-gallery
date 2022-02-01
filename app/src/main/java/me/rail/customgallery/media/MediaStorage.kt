package me.rail.customgallery.media

import me.rail.customgallery.models.Image

object MediaStorage {
    private var imagesCount = 0
    private lateinit var images: ArrayList<Image>
    private lateinit var albums: LinkedHashMap<String, ArrayList<Image>>

    fun setImagesCount(count: Int) {
        imagesCount = count
        images = ArrayList(imagesCount)
        albums = LinkedHashMap()
    }

    fun addImage(image: Image) {
        images.add(image)
    }

    fun getImages(): ArrayList<Image> {
        return images
    }

    fun addImageToAlbum(album: String, image: Image) {
        if (!checkAlbum(album)) {
            albums[album] = ArrayList()

        }

        albums[album]?.add(image)
    }

    private fun checkAlbum(album: String): Boolean {
        return albums.containsKey(album)
    }

    fun getAlbums(): LinkedHashMap<String, ArrayList<Image>> {
        return albums
    }
}