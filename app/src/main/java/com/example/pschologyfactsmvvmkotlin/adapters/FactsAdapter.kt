package com.example.pschologyfactsmvvmkotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pschologyfactsmvvmkotlin.databinding.AllFactsItemBinding

import com.example.pschologyfactsmvvmkotlin.model.Fact

class FactAdapter( private val onFactClick: (Fact) -> Unit): RecyclerView.Adapter<FactAdapter.FactViewHolder>() {

    private var facts: List<Fact> = emptyList()

    class FactViewHolder(val binding: AllFactsItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FactViewHolder {
        val binding = AllFactsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FactViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FactViewHolder, position: Int) {
        val fact = facts[position]
        holder.binding.textFact.text = fact.text
        holder.binding.textCount.text = fact.id.toString()
        holder.itemView.setOnClickListener { onFactClick(fact) }
    }

    override fun getItemCount() = facts.size

    fun submitList(factList: List<Fact>) {
        facts = factList
        notifyDataSetChanged()
    }
}