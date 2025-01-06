package com.azul.mod6prac1.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
    private lateinit var artistAdapter: ArtistAdapter

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
        //binding.titleListado.visibility = View.GONE
        //Obteniendo la instancia al repositorio
        repository = (requireActivity().application as ItemsDPApp).repository2

        //val call: Call<MutableList<GameDto>> = repository.getGames("cm/games/games_list.php")

        // Opciones para el SpinnerTipo
        val opcionesTipo = listOf("Tipos de pago", "Piso", "Sellos", "Tags")
        val adapterTipo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcionesTipo)
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipo.adapter = adapterTipo

        // Configuración del comportamiento del SpinnerTipo
        binding.spinnerTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val seleccion = opcionesTipo[position]
                actualizarOpcionesEspecial(seleccion)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada
            }
        }

        binding.spinnerEspecial.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val textoSeleccionado = parent?.getItemAtPosition(position).toString()

                // Realiza cualquier acción con el texto seleccionado
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No hacer nada
            }
        }



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

    private fun actualizarOpcionesEspecial(seleccion: String) {
        val opcionesEspecial = when (seleccion) {
            "Tipos de pago" -> listOf("Efectivo", "Tarjeta", "Otros")
            "Piso" -> listOf("1", "2")
            "Sellos" -> listOf("Participante", "No participante")
            "Tags" -> listOf("Alien Stage", "SAVE")
            else -> emptyList()
        }

        val adapterEspecial = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcionesEspecial)
        adapterEspecial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerEspecial.adapter = adapterEspecial
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}