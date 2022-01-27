package me.rail.customgallery

import android.content.Context
import android.provider.MediaStore
import android.util.Log


class MediaHandler {
    fun getRealPathFromURI(context: Context) {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN
        )

        val images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        val cur = context.contentResolver.query(
            images,
            projection,
            null,
            null,
            null
        ) ?: return

        Log.i("ListingImages", " query count=" + cur.count)

        if (cur.moveToFirst()) {
            var bucket: String
            var date: String
            val bucketColumn = cur.getColumnIndex(
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME
            )
            val dateColumn = cur.getColumnIndex(
                MediaStore.Images.Media.DATE_TAKEN
            )
            do {
                bucket = cur.getString(bucketColumn)
                date = cur.getString(dateColumn)

                Log.i(
                    "ListingImages", " bucket=" + bucket
                            + "  date_taken=" + date
                )
            } while (cur.moveToNext())
        }

        cur.close()
    }
}