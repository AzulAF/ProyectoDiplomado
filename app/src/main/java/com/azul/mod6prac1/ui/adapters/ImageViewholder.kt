package com.azul.mod6prac1.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import com.azul.mod6prac1.data.network.model.ImageDto
import com.azul.mod6prac1.databinding.ArtistElementBinding
import com.azul.mod6prac1.databinding.ImagesElementBinding
import com.bumptech.glide.Glide

class ImageViewholder(
    private val binding: ImagesElementBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ImageDto) {
        Glide.with(binding.root.context).load(item.imagen).into(binding.galleryImage)

    }
}