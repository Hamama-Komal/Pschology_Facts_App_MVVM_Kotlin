package com.example.psychologyfactsmvvmkotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pschologyfactsmvvmkotlin.databinding.CategoryItemBinding
import com.example.pschologyfactsmvvmkotlin.model.CategoryWithFacts
import kotlin.random.Random

class CategoryAdapter(
    private val onItemClick: (CategoryWithFacts) -> Unit
) : ListAdapter<CategoryWithFacts, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    class CategoryViewHolder(val binding: CategoryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val categoryWithFacts = getItem(position)
        holder.binding.textCatName.text = categoryWithFacts.category.name
        holder.binding.textCount.text = categoryWithFacts.facts.size.toString()
        Glide.with(holder.itemView.context).load(categoryWithFacts.category.imageUrl).into(holder.binding.catImage)

        // Select a random fact from the facts list
        if (categoryWithFacts.facts.isNotEmpty()) {
            val randomFactIndex = Random.nextInt(categoryWithFacts.facts.size)
            holder.binding.textCatFact.text = categoryWithFacts.facts[randomFactIndex].text
        } else {
            holder.binding.textCatFact.text = "No facts available"
        }

        holder.itemView.setOnClickListener {
            onItemClick(categoryWithFacts)
        }
    }

    class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryWithFacts>() {
        override fun areItemsTheSame(oldItem: CategoryWithFacts, newItem: CategoryWithFacts): Boolean {
            return oldItem.category == newItem.category
        }

        override fun areContentsTheSame(oldItem: CategoryWithFacts, newItem: CategoryWithFacts): Boolean {
            return oldItem == newItem
        }
    }
}
