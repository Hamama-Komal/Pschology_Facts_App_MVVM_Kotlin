package com.example.pschologyfactsmvvmkotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pschologyfactsmvvmkotlin.R
import com.example.pschologyfactsmvvmkotlin.room.Favorite

class FavoriteAdapter(
    private var favorites: List<Favorite>,
    private val onFactClick: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val factTextView: TextView = itemView.findViewById(R.id.textFact)
        val countTextView: TextView = itemView.findViewById(R.id.textCount)

        init {
            itemView.setOnClickListener {
                onFactClick(favorites[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.all_facts_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val id = position + 1
        holder.factTextView.text = favorites[position].fact
        holder.countTextView.text = id.toString()
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    fun updateData(newFavorites: List<Favorite>) {
        favorites = newFavorites
        notifyDataSetChanged()
    }

    fun getCurrentList(): List<Favorite> {
        return favorites
    }
}
