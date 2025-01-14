package com.azul.mod6prac1.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.azul.mod6prac1.R
import com.azul.mod6prac1.application.ItemsDPApp
import com.azul.mod6prac1.data.ArtistRepository
import com.azul.mod6prac1.data.network.NetworkUtils
import com.azul.mod6prac1.data.network.model.ArtistDetailDto
import com.azul.mod6prac1.databinding.FragmentArtistDetailBinding
import com.azul.mod6prac1.util.Constants
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        arguments?.let { args ->
            artistId = args.getString(ARTIST_ID)
            Log.d(Constants.LOGTAG, "Id recibido $artistId")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    //Se manda llamar ya cuando el fragment es visible en pantalla
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkNetworkAndNotify(this)
        //Obteniendo la instancia al repositorio
        repository = (requireActivity().application as ItemsDPApp).repository2

        artistId?.let{ id ->
            //Hago la llamada al endpoint para consumir los detalles del juego
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
                        tvEfectivoTEXT.text = binding.root.context.getString(R.string.efectivo)
                        tvTarjetaTEXT.text = binding.root.context.getString(R.string.tarjeta)
                        tvTransferenciaTEXT.text = binding.root.context.getString(R.string.otrospagos)
                        tvPagosTEXT.text = binding.root.context.getString(R.string.pagosDisponibles)

                        if(response.body()?.pagotarjeta == "" || response.body()?.pagotarjeta == "NO"){
                            tvTarjeta.text = binding.root.context.getString(R.string.NoDisponible)
                        } else{
                            tvTarjeta.text = response.body()?.pagotarjeta
                        }
                        if(response.body()?.transferecia !== "SI"){
                            tvTransferencia.text = binding.root.context.getString(R.string.NoDisponible)
                        } else {
                            tvTransferencia.text = response.body()?.transferecia
                        }
                        tvDescripcion.text = response.body()?.descripcion
                        tvPiso.text = response.body()?.piso
                        tvMesa.text = response.body()?.mesa
                        tvSellos.text = response.body()?.sellos
                        tvEfectivo.text = response.body()?.pagoefectivo
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
    fun checkNetworkAndNotify(fragment: Fragment) {
        val context = fragment.requireContext()
        if (!NetworkUtils.isNetworkAvailable(context)) {
            Toast.makeText(context, binding.root.context.getString(R.string.SinConexion), Toast.LENGTH_LONG).show()
        } else if (NetworkUtils.isUsingMobileData(context)) {
            Snackbar.make(
                fragment.requireView(), // Vista raíz del fragmento
                binding.root.context.getString(R.string.DatosMoviles),
                Snackbar.LENGTH_LONG
            ).show()
        }
    }


}