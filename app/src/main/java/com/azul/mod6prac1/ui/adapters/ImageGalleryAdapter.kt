package com.azul.mod6prac1.ui.adapters
// ImageGalleryAdapter.kt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azul.mod6prac1.data.network.model.ArtistDto
import com.azul.mod6prac1.data.network.model.ImageDto
import com.azul.mod6prac1.data.network.model.ItemDto
import com.azul.mod6prac1.databinding.ArtistElementBinding
import com.azul.mod6prac1.databinding.ImagesElementBinding
import com.azul.mod6prac1.ui.adapters.ImageViewholder


class ImageGalleryAdapter(
    private val images: MutableList<ImageDto>,
    private val onImageClicked: (ImageDto) -> Unit
) : RecyclerView.Adapter<ImageViewholder>() {

    // Original list (unfiltered)
    private val maps: List<ImageDto> = images.toList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewholder {
        val binding = ImagesElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewholder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewholder, position: Int) {
        // Set the image resource using View Binding
        val image = maps[position]
        holder.bind(image)
        holder.itemView.setOnClickListener {
            // Handle artist item click
            onImageClicked(image)
        }

    }

    override fun getItemCount(): Int = maps.size
}
