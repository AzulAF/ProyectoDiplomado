package com.azul.mod6prac1.data.network.model
import com.google.gson.annotations.SerializedName

data class ArtistDetailDto (
    @SerializedName("id")
    var id: String? = null,

    @SerializedName("piso")
    var piso: String? = null,

    @SerializedName("mesa")
    var mesa: String? = null,

    @SerializedName("nombre")
    var nombre: String? = null,

    @SerializedName("sellos")
    var sellos: String? = null,

    @SerializedName("pagoefectivo")
    var pagoefectivo: String? = null,

    @SerializedName("pagotarjeta")
    var pagotarjeta: String? = null,

    @SerializedName("transferencia")
    var transferecia: String? = null,

    @SerializedName("imagen")
    var imagen: String? = null,

    @SerializedName("descripcion")
    var descripcion: String? = null,

    @SerializedName("tags")
    var fandomtags: String? = null


)
