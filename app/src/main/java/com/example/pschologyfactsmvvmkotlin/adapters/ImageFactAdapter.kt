package com.example.pschologyfactsmvvmkotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pschologyfactsmvvmkotlin.databinding.ImageItemBinding
import com.example.pschologyfactsmvvmkotlin.model.ImageFact

class ImageFactAdapter(
    private val clickListener: (ImageFact, Int) -> Unit
) : ListAdapter<ImageFact, ImageFactAdapter.ImageFactViewHolder>(ImageFactDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFactViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageFactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageFactViewHolder, position: Int) {
        holder.bind(getItem(position), position, clickListener)
    }

    class ImageFactViewHolder(private val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imageFact: ImageFact, position: Int, clickListener: (ImageFact, Int) -> Unit) {
            Glide.with(itemView.context).load(imageFact.imageUrl).into(binding.factImage)
            binding.root.setOnClickListener {
                clickListener(imageFact, position)
            }
        }
    }

    class ImageFactDiffCallback : DiffUtil.ItemCallback<ImageFact>() {
        override fun areItemsTheSame(oldItem: ImageFact, newItem: ImageFact): Boolean {
            return oldItem.imageUrl == newItem.imageUrl
        }

        override fun areContentsTheSame(oldItem: ImageFact, newItem: ImageFact): Boolean {
            return oldItem == newItem
        }
    }
}
