package com.azul.mod6prac1.data.network.model

import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("imagen")
    var imagen: String? = null
)
