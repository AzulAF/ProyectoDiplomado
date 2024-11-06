package com.azul.mod6prac1.data
import com.azul.mod6prac1.data.network.ArtistsApi
import com.azul.mod6prac1.data.network.ImageApi
import com.azul.mod6prac1.data.network.model.ArtistDetailDto
import com.azul.mod6prac1.data.network.model.ArtistDto
import com.azul.mod6prac1.data.network.model.ImageDto
import retrofit2.Call
import retrofit2.Retrofit

class ImagesRepository(private val retrofit: Retrofit) {

    private val gamesApi: ImageApi = retrofit.create(ImageApi::class.java)
    //Para Apiary
    fun getImagesApiary(): Call<MutableList<ImageDto>> = gamesApi.getImagesApiary()

    fun getImagesDetailApiary(id: String?): Call<ImageDto> =
        gamesApi.getImagesDetailApiary(id)
}