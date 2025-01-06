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

    val repositoryNoticias by lazy {
        ImagesRepository(retrofit)
    }

    val repositoryAvisos by lazy {
        ImagesRepository(retrofit)
    }
    val repositoryTalleres by lazy {
        ImagesRepository(retrofit)
    }
    val repositoryHorarios by lazy {
        ImagesRepository(retrofit)
    }
    val repositoryInvitados by lazy {
        ImagesRepository(retrofit)
    }
    val repositoryMapas2 by lazy {
        ImagesRepository(retrofit)
    }


    private val retrofit by lazy{
        RetrofitHelper().getRetrofit()
    }
}