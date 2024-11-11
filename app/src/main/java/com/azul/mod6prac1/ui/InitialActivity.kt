package com.azul.mod6prac1.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.azul.mod6prac1.R
import com.azul.mod6prac1.application.ItemsDPApp
import com.azul.mod6prac1.data.ArtistRepository
import com.azul.mod6prac1.data.ImagesRepository
import com.azul.mod6prac1.data.network.model.ArtistDto
import com.azul.mod6prac1.data.network.model.ImageDto
import com.azul.mod6prac1.databinding.ActivityInitialBinding
import com.azul.mod6prac1.ui.adapters.ArtistAdapter
import com.azul.mod6prac1.ui.adapters.ImageGalleryAdapter
import com.azul.mod6prac1.ui.fragments.ArtistDetailFragment
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

        binding.buttonFirst.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.buttonSecond.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
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
                        adapter = ImageGalleryAdapter(images) { image ->
                            // Trigger the action to view the image details
                            image.id?.let { id ->
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, ImageDetailFragment.newInstance(id))
                                    .addToBackStack(null)
                                    .commit()
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
}