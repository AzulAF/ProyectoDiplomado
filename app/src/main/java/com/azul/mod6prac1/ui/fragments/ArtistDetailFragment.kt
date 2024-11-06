package com.azul.mod6prac1.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.azul.mod6prac1.R
import com.azul.mod6prac1.application.ItemsDPApp
import com.azul.mod6prac1.data.ArtistRepository
import com.azul.mod6prac1.data.network.model.ArtistDetailDto
import com.azul.mod6prac1.databinding.FragmentArtistDetailBinding
import com.azul.mod6prac1.util.Constants
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARTIST_ID = "artist_id"

class ArtistDetailFragment : Fragment() {

    private var artistId: String? = null

    private var _binding: FragmentArtistDetailBinding? = null
    private val binding get()  = _binding!!

    private lateinit var repository: ArtistRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            artistId = args.getString(ARTIST_ID)
            Log.d(Constants.LOGTAG, "Id recibido $artistId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentArtistDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Se manda llamar ya cuando el fragment es visible en pantalla
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Obteniendo la instancia al repositorio
        repository = (requireActivity().application as ItemsDPApp).repository2

        artistId?.let{ id ->
            //Hago la llamada al endpoint para consumir los detalles del juego

            //val call: Call<GameDetailDto> = repository.getGameDetail(id)

            //Para apiary
            val call: Call<ArtistDetailDto> = repository.getArtistDetailApiary(id)

            call.enqueue(object: Callback<ArtistDetailDto> {
                override fun onResponse(p0: Call<ArtistDetailDto>, response: Response<ArtistDetailDto>) {

                    binding.apply {
                        pbLoading.visibility = View.GONE

                        //Aquí utilizamos la respuesta exitosa y asignamos los valores a las vistas
                        tvName.text = response.body()?.nombre

                        Glide.with(requireActivity())
                            .load(response.body()?.imagen)
                            .into(ivImage)
                        tvPisoTEXT.text = binding.root.context.getString(R.string.piso)
                        tvMesaTEXT.text = binding.root.context.getString(R.string.mesa)
                        tvSellosTEXT.text = binding.root.context.getString(R.string.sellos)
                        tvTarjetaTEXT.text = binding.root.context.getString(R.string.tarjeta)
                        tvEfectivoTEXT.text = binding.root.context.getString(R.string.efectivo)
                        tvTransferenciaTEXT.text = binding.root.context.getString(R.string.transferencia)

                        tvPiso.text = response.body()?.piso
                        tvMesa.text = response.body()?.mesa
                        tvSellos.text = response.body()?.sellos
                        tvTarjeta.text = response.body()?.pagotarjeta
                        tvEfectivo.text = response.body()?.pagoefectivo
                        tvTransferencia.text = response.body()?.transferecia
                    }



                }

                override fun onFailure(p0: Call<ArtistDetailDto>, p1: Throwable) {
                    //Manejo del error de conexión
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
        fun newInstance(artistId: String) =
            ArtistDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARTIST_ID, artistId)
                }
            }
    }

}