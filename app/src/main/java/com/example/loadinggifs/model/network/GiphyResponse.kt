package com.example.loadinggifs.model.network

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
data class GifResponse(val data: List<ImageData>)


@Parcelize
@Serializable
data class ImageData(
    val id:String,
    val images:Image,
    val title:String
):Parcelable

@Parcelize
@Serializable
data class Image(val downsized_medium : ImageProperties):Parcelable

@Parcelize
@Serializable
data class ImageProperties(val url : String):Parcelable