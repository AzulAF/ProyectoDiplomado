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

    // Original list (unfiltered)
    private val originalArtists: List<ArtistDto> = artists.toList()

    // List to display in RecyclerView, initially showing all artists
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
            // Handle artist item click
            onArtistClicked(artist)
        }
    }

    // Method to filter list by mesa == 1
    fun showFilteredList() {
        displayedArtists.clear()
        displayedArtists.addAll(originalArtists.filter { it.mesa == "1" })
        notifyDataSetChanged()
    }

    // Method to show the original (unfiltered) list
    fun showOriginalList() {
        displayedArtists.clear()
        displayedArtists.addAll(originalArtists)
        notifyDataSetChanged()
    }
}
