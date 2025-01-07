package com.azul.mod6prac1.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.azul.mod6prac1.R
import com.azul.mod6prac1.application.ItemsDPApp
import com.azul.mod6prac1.data.ImagesRepository
import com.azul.mod6prac1.data.network.model.ImageDto
import com.azul.mod6prac1.databinding.ActivityInitialBinding
import com.azul.mod6prac1.ui.adapters.ImageGalleryAdapter
import com.azul.mod6prac1.ui.adapters.ImageGalleryAdapterBasic
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private lateinit var binding: ActivityInitialBinding

class InitialActivity : AppCompatActivity() {
    private lateinit var repository: ImagesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitialBinding.inflate(layoutInflater)
        setContentView(binding.root)
//Marcadores
        binding.buttonFirst.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
//Lista de artistas completa
        binding.buttonSecond.setOnClickListener {
            val intent = Intent(this, ArtistListsActivity::class.java)
            startActivity(intent)
        }
        val linksMap = mapOf(
            binding.imageButtonAvisos to "https://private-a4ba8-dadmtarea.apiary-mock.com/event/avisos",
            binding.imageButtonTalleres to "https://private-a4ba8-dadmtarea.apiary-mock.com/event/talleres",
            binding.imageButtonMap to "https://private-a4ba8-dadmtarea.apiary-mock.com/event/mapas2",
            binding.imageButtonInvitados to "https://private-a4ba8-dadmtarea.apiary-mock.com/event/invitados",
            binding.imageButtonNoticias to "https://private-a4ba8-dadmtarea.apiary-mock.com/event/noticias",
            binding.imageButtonHorarios to "https://private-a4ba8-dadmtarea.apiary-mock.com/event/horarios"
        )
        linksMap.forEach { (button, link) ->
            button.setOnClickListener {
                fetchAndDisplayData(link)
            }
        }


        repository = (this.application as ItemsDPApp).repositoryMaps
        //Para apiary
        val call: Call<MutableList<ImageDto>> = repository.getImagesApiary()

        call.enqueue(object: Callback<MutableList<ImageDto>> {
            override fun onResponse(
                p0: Call<MutableList<ImageDto>>,
                response: Response<MutableList<ImageDto>>
            ) {
                response.body()?.let{ images ->

                    //Le pasamos los juegos al recycler view y lo instanciamos
                    binding.rvGallery.apply {
                        layoutManager = LinearLayoutManager(this@InitialActivity, LinearLayoutManager.HORIZONTAL, false)
                        //layoutManager = GridLayoutManager(requireContext(), 3)
                        adapter = ImageGalleryAdapterBasic(images) { image ->
                            // Trigger the action to view the image details
                            image.id?.let { id ->
//                                supportFragmentManager.beginTransaction()
//                                    .replace(R.id.fragment_container, ImageDetailFragment.newInstance(id))
//                                    .addToBackStack(null)
//                                    .commit()
                            }
                        }
                    }
                }
            }
            override fun onFailure(p0: Call<MutableList<ImageDto>>, p1: Throwable) {
                Toast.makeText(this@InitialActivity,"Error, no hay conexion",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchAndDisplayData(link: String) {
        val call: Call<MutableList<ImageDto>> = repository.getImagesFromUrl(link) // Modifica el repositorio para aceptar URLs dinámicas

        call.enqueue(object : Callback<MutableList<ImageDto>> {
            override fun onResponse(
                call: Call<MutableList<ImageDto>>,
                response: Response<MutableList<ImageDto>>
            ) {
                response.body()?.let { images ->
                    // Navegar al fragmento con la lista de imágenes
                    val fragment = ImageDetailFragment.newInstance(images)
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit()

                }
            }

            override fun onFailure(call: Call<MutableList<ImageDto>>, t: Throwable) {
                Toast.makeText(this@InitialActivity, "Error al cargar los datos", Toast.LENGTH_SHORT).show()
            }
        })
    }
}