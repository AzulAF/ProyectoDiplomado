package com.azul.mod6prac1.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.azul.mod6prac1.R
import com.azul.mod6prac1.application.ItemsDPApp
import com.azul.mod6prac1.data.ArtistRepository
import com.azul.mod6prac1.data.ImagesRepository
import com.azul.mod6prac1.data.network.model.ArtistDetailDto
import com.azul.mod6prac1.data.network.model.ImageDto
import com.azul.mod6prac1.databinding.FragmentArtistDetailBinding
import com.azul.mod6prac1.databinding.FragmentImageDetailBinding
import com.azul.mod6prac1.ui.fragments.ArtistDetailFragment
import com.azul.mod6prac1.util.Constants
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val IMAGE_ID = "image_id"


class ImageDetailFragment : Fragment() {

    private var imageId: String? = null

    private var _binding: FragmentImageDetailBinding? = null
    private val binding get()  = _binding!!

    private lateinit var repository: ImagesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args->
            imageId = args.getString(IMAGE_ID)
            Log.d(Constants.LOGTAG, "Id recibido de imagen $imageId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentImageDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Obteniendo la instancia al repositorio
        repository = (requireActivity().application as ItemsDPApp).repositoryMaps

        imageId?.let{ id ->
            //Hago la llamada al endpoint para consumir los detalles del juego

            //val call: Call<GameDetailDto> = repository.getGameDetail(id)

            //Para apiary
            val call: Call<ImageDto> = repository.getImagesDetailApiary(id)

            call.enqueue(object: Callback<ImageDto> {
                override fun onResponse(p0: Call<ImageDto>, response: Response<ImageDto>) {
                    binding.apply {
                        Glide.with(requireActivity())
                            .load(response.body()?.imagen)
                            .into(fullscreenImage)
                    }
                }

                override fun onFailure(p0: Call<ImageDto>, p1: Throwable) {
                    Log.d(Constants.LOGTAG, "Error de conexión")
                }

            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(imageId: String) =
            ImageDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(IMAGE_ID, imageId)
                }
            }
    }


}