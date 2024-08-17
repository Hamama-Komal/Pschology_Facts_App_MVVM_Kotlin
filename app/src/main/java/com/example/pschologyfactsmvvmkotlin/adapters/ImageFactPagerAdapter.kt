package com.example.pschologyfactsmvvmkotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pschologyfactsmvvmkotlin.R
import com.example.pschologyfactsmvvmkotlin.model.ImageFact

class ImageFactPagerAdapter(private val imageFacts: List<ImageFact>) :
    RecyclerView.Adapter<ImageFactPagerAdapter.ImageFactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageFactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_fact_item, parent, false)
        return ImageFactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageFactViewHolder, position: Int) {
        val imageFact = imageFacts[position]
        Glide.with(holder.imageView.context).load(imageFact.imageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int = imageFacts.size

    class ImageFactViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.imageFact)
    }
}