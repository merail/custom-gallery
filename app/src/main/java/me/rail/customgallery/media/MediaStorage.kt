package me.rail.customgallery.media

import me.rail.customgallery.models.Image

object MediaStorage {
    private var imagesCount = 0
    private lateinit var images: ArrayList<Image>

    fun setImagesCount(count: Int) {
        imagesCount = count
        images = ArrayList(imagesCount)
    }

    fun addImage(image: Image) {
        images.add(image)
    }

    fun getImages(): ArrayList<Image> {
        return images
    }
}