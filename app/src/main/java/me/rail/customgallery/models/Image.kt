package me.rail.customgallery.models

import android.net.Uri

data class Image(override val uri: Uri, override val name: String, override val album: String): Media(uri, name, album)
