package com.azul.mod6prac1.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.azul.mod6prac1.R
import com.azul.mod6prac1.application.ItemsDPApp
import com.azul.mod6prac1.data.ArtistRepository
import com.azul.mod6prac1.data.network.model.ArtistDto
import com.azul.mod6prac1.databinding.FragmentArtistListBinding
import com.azul.mod6prac1.ui.adapters.ArtistAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ArtistListFragment  : Fragment() {

    private var _binding: FragmentArtistListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: ArtistRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArtistListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.titleListado.visibility = View.GONE
        //Obteniendo la instancia al repositorio
        repository = (requireActivity().application as ItemsDPApp).repository2

        //val call: Call<MutableList<GameDto>> = repository.getGames("cm/games/games_list.php")

        //Para apiary
        val call: Call<MutableList<ArtistDto>> = repository.getArtistsApiary()

        call.enqueue(object: Callback<MutableList<ArtistDto>> {
            override fun onResponse(
                p0: Call<MutableList<ArtistDto>>,
                response: Response<MutableList<ArtistDto>>
            ) {
                binding.pbLoading.visibility = View.GONE

                response.body()?.let{ artists ->

                    //Le pasamos los juegos al recycler view y lo instanciamos
                    binding.rvItems.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        //layoutManager = GridLayoutManager(requireContext(), 3)
                        adapter = ArtistAdapter(artists){ artist ->
                            //Aquí realizamos la acción para ir a ver los detalles del juego
                            artist.id?.let{ id ->
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, ArtistDetailFragment.newInstance(id))
                                    .addToBackStack(null)
                                    .commit()
                            }
                        }

                    }

                }
            }

            override fun onFailure(p0: Call<MutableList<ArtistDto>>, p1: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error: No hay conexión disponible",
                    Toast.LENGTH_SHORT
                ).show()
                binding.pbLoading.visibility = View.GONE
                binding.titleListado.visibility = View.VISIBLE
            }

        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}