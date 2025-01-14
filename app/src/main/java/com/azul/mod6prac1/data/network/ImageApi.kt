package com.azul.mod6prac1.data.network
import com.azul.mod6prac1.data.network.model.ImageDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ImageApi {

    @GET("event/maps")
    fun getImagesApiary(): Call<MutableList<ImageDto>>

    //https://private-a649a-games28.apiary-mock.com/games/game_detail/21357
    @GET("event/map/{id}")
    fun getImagesDetailApiary(
        @Path("id") id: String?
    ): Call<ImageDto>

    @GET
    fun getImagesFromUrl(@Url url: String): Call<MutableList<ImageDto>>
}