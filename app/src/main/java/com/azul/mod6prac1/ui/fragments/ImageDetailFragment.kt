package com.azul.mod6prac1.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.azul.mod6prac1.R
import com.azul.mod6prac1.data.network.NetworkUtils
import com.azul.mod6prac1.data.network.model.ImageDto
import com.azul.mod6prac1.ui.adapters.ImageGalleryAdapter
import com.google.android.material.snackbar.Snackbar

class ImageDetailFragment : Fragment() {
    private lateinit var images: List<ImageDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkNetworkAndNotify(this)
        arguments?.let {
            images = it.getParcelableArrayList(ARG_IMAGES) ?: emptyList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_detail, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ImageGalleryAdapter(images) { image ->
            // Aquí puedes agregar funcionalidad para el click en una imagen
        }

        return view
    }

    companion object {
        private const val ARG_IMAGES = "images"

        fun newInstance(images: List<ImageDto>) = ImageDetailFragment().apply {
            arguments = Bundle().apply {
                putParcelableArrayList(ARG_IMAGES, ArrayList(images))
            }
        }
    }

    fun checkNetworkAndNotify(fragment: Fragment) {
        val context = fragment.requireContext()
        if (!NetworkUtils.isNetworkAvailable(context)) {
            Toast.makeText(context, "No tienes conexión a Internet.", Toast.LENGTH_LONG).show()
        } else if (NetworkUtils.isUsingMobileData(context)) {
            Snackbar.make(
                fragment.requireView(), // Vista raíz del fragmento
                "Estás utilizando datos móviles.",
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

}
