package me.rail.customgallery.media

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import me.rail.customgallery.models.Image
import me.rail.customgallery.models.Media
import me.rail.customgallery.models.Video


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
        MediaStorage.setImagesCount()

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

    fun findVideos(context: Context) {
        val projection = arrayOf(
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.DISPLAY_NAME
        )
        val videosUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val sortOrder = "date_added DESC"

        val cursor = context.contentResolver.query(
            videosUri,
            projection,
            null,
            null,
            sortOrder
        ) ?: return

        Log.i("ListingVideos", " query count=" + cursor.count)
        MediaStorage.setVideosCount()

        if (cursor.moveToFirst()) {
            var id: Long
            var name: String
            var bucket: String

            val idColumn = cursor.getColumnIndex(MediaStore.Video.Media._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)
            val bucketColumn = cursor.getColumnIndex(MediaStore.Video.Media.BUCKET_DISPLAY_NAME)

            do {
                id = cursor.getLong(idColumn)
                val uriVideo = Uri.withAppendedPath(videosUri, "" + id)
                name = cursor.getString(nameColumn)
                bucket = cursor.getString(bucketColumn)

                val video = Video(uriVideo, name, bucket)

                MediaStorage.addVideo(video)
                MediaStorage.addVideoToAlbum(bucket, video)
            } while (cursor.moveToNext())
        }

        cursor.close()
    }

    fun findMedia(context: Context) {
        val bucketFileColumn = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            MediaStore.Files.FileColumns.DATA
        } else {
            MediaStore.Files.FileColumns.BUCKET_DISPLAY_NAME
        }

        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            bucketFileColumn,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.MEDIA_TYPE,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.TITLE
        )

        val sortOrder = "date_added DESC"

        // Return only video and image metadata.
        val selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
                + " OR "
                + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
                + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)

        val queryUri = MediaStore.Files.getContentUri("external")

        val cursor = context.contentResolver.query(
            queryUri,
            projection,
            selection,
            null,
            sortOrder
        ) ?: return

        Log.i("ListingVideos", " query count=" + cursor.count)
        MediaStorage.setMediasCount(cursor.count)
        MediaStorage.setImagesCount()
        MediaStorage.setVideosCount()

        if (cursor.moveToFirst()) {
            var id: Long
            var uri: Uri
            var name: String
            var bucket: String

            val idColumn = cursor.getColumnIndex(MediaStore.Files.FileColumns._ID)
            val nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DISPLAY_NAME)
            val bucketColumn = cursor.getColumnIndex(bucketFileColumn)
            val typeColumn = cursor.getColumnIndex(MediaStore.Files.FileColumns.MEDIA_TYPE)

            do {
                id = cursor.getLong(idColumn)
                uri = Uri.withAppendedPath(queryUri, "" + id)
                name = cursor.getString(nameColumn)
                bucket = cursor.getString(bucketColumn)
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    val pathList = bucket.split("/")
                    bucket = pathList[pathList.lastIndex - 1]
                }
                val type = cursor.getInt(typeColumn)

                Log.d("AAAAAAAAAAAA", bucket)
                Log.d("BBBBBBBBBBBB", name)
                Log.d("CCCCCCCCCCCC", uri.toString())
                Log.d("DDDDDDDDDDDD", type.toString())

                if (type == 1) {
                    val image = Image(uri, name, bucket)
                    MediaStorage.addImage(image)
                    MediaStorage.addImageToAlbum(bucket, image)
                    MediaStorage.addMedia(image)
                    MediaStorage.addMediaToAlbum(bucket, image)
                } else {
                    val video = Video(uri, name, bucket)
                    MediaStorage.addVideo(video)
                    MediaStorage.addVideoToAlbum(bucket, video)
                    MediaStorage.addMedia(video)
                    MediaStorage.addMediaToAlbum(bucket, video)
                }
            } while (cursor.moveToNext())
        }

        cursor.close()
    }
}