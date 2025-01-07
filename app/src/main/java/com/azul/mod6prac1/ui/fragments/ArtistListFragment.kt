package com.azul.mod6prac1.ui.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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

class ArtistListFragment : Fragment() {

    private var _binding: FragmentArtistListBinding? = null
    private val binding get() = _binding!!

    private lateinit var repository: ArtistRepository
    private lateinit var artistAdapter: ArtistAdapter
    private var artistsList: MutableList<ArtistDto> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar repositorio
        repository = (requireActivity().application as ItemsDPApp).repository2

        // Configuración inicial del adaptador con una lista vacía
        artistAdapter = ArtistAdapter(mutableListOf()) { artist ->
            artist.id?.let { id ->
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, ArtistDetailFragment.newInstance(id))
                    .addToBackStack(null)
                    .commit()
            }
        }

        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = artistAdapter

        // Configurar los filtros y spinners
        setupFilters()

        // Llamar a la API
        fetchArtistsFromApi()
    }

    private fun setupFilters() {
        val opcionesTipo = listOf("Piso", "Sellos")
        val adapterTipo = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcionesTipo)
        adapterTipo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipo.adapter = adapterTipo

        binding.spinnerTipo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val seleccion = opcionesTipo[position]
                actualizarOpcionesEspecial(seleccion)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinnerEspecial.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                filtrarLista()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.editTextNombre.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarLista()
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.editTextNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtrarLista()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun fetchArtistsFromApi() {
        val call: Call<MutableList<ArtistDto>> = repository.getArtistsApiary()
        call.enqueue(object : Callback<MutableList<ArtistDto>> {
            override fun onResponse(call: Call<MutableList<ArtistDto>>, response: Response<MutableList<ArtistDto>>) {
                binding.pbLoading.visibility = View.GONE
                response.body()?.let { artists ->
                    artistsList = artists.toMutableList()
                    artistAdapter.updateList(artistsList) // Actualizar la lista completa en el adaptador
                }
            }

            override fun onFailure(call: Call<MutableList<ArtistDto>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error: No hay conexión disponible",
                    Toast.LENGTH_SHORT
                ).show()
                binding.pbLoading.visibility = View.GONE
            }
        })
    }

    private fun actualizarOpcionesEspecial(seleccion: String) {
        val opcionesEspecial = when (seleccion) {
            "Piso" -> listOf("1", "2")
            "Sellos" -> listOf("SI", "NO")
            else -> emptyList()
        }

        val adapterEspecial = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, opcionesEspecial)
        adapterEspecial.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerEspecial.adapter = adapterEspecial
    }

    private fun filtrarLista() {
        val tipoSeleccionado = binding.spinnerTipo.selectedItem?.toString() ?: ""
        val especialSeleccionado = binding.spinnerEspecial.selectedItem?.toString() ?: ""
        val nombre = binding.editTextNombre.text.toString()
        val numero = binding.editTextNumber.text.toString()

        // Si no hay filtros activos, mostrar la lista completa
        if (tipoSeleccionado.isEmpty() && especialSeleccionado.isEmpty() &&
            nombre.isEmpty() && numero.isEmpty()
        ) {
            artistAdapter.updateList(artistsList)
            return
        }

        // Filtrar la lista según los criterios seleccionados
        val filteredList = artistsList.filter { artist ->
            var coincide = true

            if (tipoSeleccionado == "Piso") {
                coincide = coincide && artist.piso == especialSeleccionado
            } else if (tipoSeleccionado == "Sellos") {
                coincide = coincide && artist.sellos == especialSeleccionado
            }

            if (nombre.isNotEmpty()) {
                coincide = coincide && artist.nombre!!.contains(nombre, ignoreCase = true)
            }

            if (numero.isNotEmpty()) {
                coincide = coincide && artist.mesa == numero
            }

            coincide
        }

        artistAdapter.updateList(filteredList)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
