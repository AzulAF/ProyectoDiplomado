package com.azul.mod6prac1.ui.adapters
// ImageGalleryAdapter.kt
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azul.mod6prac1.R
import com.azul.mod6prac1.data.network.model.ArtistDto
import com.azul.mod6prac1.data.network.model.ImageDto
import com.azul.mod6prac1.data.network.model.ItemDto
import com.azul.mod6prac1.databinding.ArtistElementBinding
import com.azul.mod6prac1.databinding.ImagesElementBinding
import com.azul.mod6prac1.ui.adapters.ImageViewholder
import com.bumptech.glide.Glide

class ImageGalleryAdapter(
    private val images: List<ImageDto>,
    private val onImageClicked: (ImageDto) -> Unit
) : RecyclerView.Adapter<ImageGalleryAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImagesElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = images[position]
        holder.bind(image)
        holder.itemView.setOnClickListener {
            onImageClicked(image)
        }
    }

    override fun getItemCount(): Int = images.size

    class ImageViewHolder(private val binding: ImagesElementBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageDto: ImageDto) {
            // Cargar la imagen desde la URL utilizando Glide
            Glide.with(binding.root.context)
                .load(imageDto.imagen) // URL de la imagen
                .placeholder(R.drawable.artist_placeholder) // Imagen de carga // Imagen en caso de error
                .into(binding.galleryImage) // `imageView` del layout
        }
    }
}
