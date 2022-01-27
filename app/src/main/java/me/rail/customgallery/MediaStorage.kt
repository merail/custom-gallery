package me.rail.customgallery

import android.util.Log

object MediaStorage {
    private var imagesCount = 0
    private lateinit var images: ArrayList<Image>

    fun setImagesCount(count: Int) {
        imagesCount = count
        images = ArrayList(imagesCount)
    }

    fun addImage(image: Image) {
        images.add(image)
        Log.d("AAAAAAAAAAAA", image.toString())
    }
}