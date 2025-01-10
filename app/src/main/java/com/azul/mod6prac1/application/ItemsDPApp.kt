package com.azul.mod6prac1.application

import android.app.Application
import com.azul.mod6prac1.data.ArtistRepository
import com.azul.mod6prac1.data.ImagesRepository
import com.azul.mod6prac1.data.ItemRepository
import com.azul.mod6prac1.data.db.ItemDatabase
import com.azul.mod6prac1.data.network.RetrofitHelper

class ItemsDPApp: Application() {

    private val database by lazy{
        ItemDatabase.getDatabase(this@ItemsDPApp)
    }

    val repository by lazy {
        ItemRepository(database.itemDao())

    }

    val repository2 by lazy {
        ArtistRepository(retrofit)
    }

    val repositoryMaps by lazy {
        ImagesRepository(retrofit)
    }


    private val retrofit by lazy{
        RetrofitHelper().getRetrofit()
    }
}