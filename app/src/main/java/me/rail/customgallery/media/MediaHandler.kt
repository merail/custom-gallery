package me.rail.customgallery.media

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import me.rail.customgallery.models.Image


class MediaHandler {
    fun findImages(context: Context) {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DISPLAY_NAME
        )
        val imagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val sortOrder = "date_added DESC"

        val cursor = context.contentResolver.query(
            imagesUri,
            projection,
            null,
            null,
            sortOrder
        ) ?: return

        Log.i("ListingImages", " query count=" + cursor.count)
        MediaStorage.setImagesCount(cursor.count)

        if (cursor.moveToFirst()) {
            var id: Long
            var name: String
            var bucket: String

            val idColumn = cursor.getColumnIndex(MediaStore.Images.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            val bucketColumn = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

            do {
                id = cursor.getLong(idColumn)
                val uriImage = Uri.withAppendedPath(imagesUri, "" + id)
                name = cursor.getString(nameColumn)
                bucket = cursor.getString(bucketColumn)

                val image = Image(uriImage, name, bucket)

                MediaStorage.addImage(image)
                MediaStorage.addImageToAlbum(bucket, image)
            } while (cursor.moveToNext())
        }

        cursor.close()
    }
}