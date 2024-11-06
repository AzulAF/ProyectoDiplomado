package com.azul.mod6prac1.data

import com.azul.mod6prac1.data.network.ArtistsApi
import com.azul.mod6prac1.data.network.model.ArtistDetailDto
import com.azul.mod6prac1.data.network.model.ArtistDto
import retrofit2.Call
import retrofit2.Retrofit

class ArtistRepository (
    private val retrofit: Retrofit
) {

    private val gamesApi: ArtistsApi = retrofit.create(ArtistsApi::class.java)


    //Para Apiary
    fun getArtistsApiary(): Call<MutableList<ArtistDto>> = gamesApi.getArtistsApiary()

    fun getArtistDetailApiary(id: String?): Call<ArtistDetailDto> =
        gamesApi.getArtistDetailApiary(id)
}