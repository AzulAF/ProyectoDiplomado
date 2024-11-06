package com.azul.mod6prac1.data.network

import com.azul.mod6prac1.data.network.model.ArtistDetailDto
import com.azul.mod6prac1.data.network.model.ArtistDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ArtistsApi {

    @GET("event/tables")
    fun getArtistsApiary(): Call<MutableList<ArtistDto>>

    //https://private-a649a-games28.apiary-mock.com/games/game_detail/21357
    @GET("event/tables/{id}")
    fun getArtistDetailApiary(
        @Path("id") id: String?
    ): Call<ArtistDetailDto>
}