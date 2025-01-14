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
        arguments?.let {
            images = it.getParcelableArrayList(ARG_IMAGES) ?: emptyList()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_image_detail, container, false)
        checkNetworkAndNotify(view)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = ImageGalleryAdapter(images) { image ->
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

    private fun checkNetworkAndNotify(view: View) {
        val context = requireContext()
        when {
            !NetworkUtils.isNetworkAvailable(context) -> {
                Snackbar.make(
                    view,
                    requireContext().getString(R.string.SinConexion),
                    Snackbar.LENGTH_LONG
                ).show()
            }
            NetworkUtils.isUsingMobileData(context) -> {
                Snackbar.make(
                    view,
                    requireContext().getString(R.string.DatosMoviles)
                    ,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
}


