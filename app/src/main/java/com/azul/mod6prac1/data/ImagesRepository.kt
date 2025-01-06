package com.azul.mod6prac1.data
import com.azul.mod6prac1.data.network.ArtistsApi
import com.azul.mod6prac1.data.network.ImageApi
import com.azul.mod6prac1.data.network.model.ArtistDetailDto
import com.azul.mod6prac1.data.network.model.ArtistDto
import com.azul.mod6prac1.data.network.model.ImageDto
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

class ImagesRepository(private val retrofit: Retrofit) {

    private val gamesApi: ImageApi = retrofit.create(ImageApi::class.java)
    //Para Apiary
    fun getImagesApiary(): Call<MutableList<ImageDto>> = gamesApi.getImagesApiary()

    fun getImagesDetailApiary(id: String?): Call<ImageDto> =
        gamesApi.getImagesDetailApiary(id)

    fun getImagesFromUrl(url: String): Call<MutableList<ImageDto>> =
        gamesApi.getImagesFromUrl(url)

}