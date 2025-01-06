package com.azul.mod6prac1.data.network.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
//data class ImageDto(
//    @SerializedName("id")
//    var id: String? = null,
//
//    @SerializedName("imagen")
//    var imagen: String? = null
//      )
    @Parcelize
data class ImageDto(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("imagen")
    var imagen: String? = null,
) : Parcelable


