package com.azul.mod6prac1.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azul.mod6prac1.data.network.model.ArtistDto
import com.azul.mod6prac1.databinding.ArtistElementBinding
class ArtistAdapter(
    private val artists: MutableList<ArtistDto>,
    private val onArtistClicked: (ArtistDto) -> Unit
) : RecyclerView.Adapter<ArtistViewHolder>() {

    // Lista original (sin filtrar)
    private val originalArtists: List<ArtistDto> = artists.toList()

    // Lista mostrada en el RecyclerView
    private val displayedArtists: MutableList<ArtistDto> = artists.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ArtistElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }

    override fun getItemCount(): Int = displayedArtists.size

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val artist = displayedArtists[position]
        holder.bind(artist)

        holder.itemView.setOnClickListener {
            onArtistClicked(artist)
        }
    }

    // Método para filtrar la lista
    fun updateList(newList: List<ArtistDto>) {
        displayedArtists.clear() // Limpiar la lista mostrada
        displayedArtists.addAll(newList) // Añadir la lista filtrada
        notifyDataSetChanged() // Notificar cambios al adaptador
    }

    // Método para mostrar la lista original completa
    fun showOriginalList() {
        updateList(originalArtists) // Usa updateList para restaurar la lista original
    }

    // Método para obtener la lista original (opcional, si necesitas acceso en el Fragment)
    fun getOriginalList(): List<ArtistDto> {
        return originalArtists
    }
}
