package me.rail.customgallery.models

import android.net.Uri

data class Video(override val uri: Uri, override val name: String): Media(uri, name)
