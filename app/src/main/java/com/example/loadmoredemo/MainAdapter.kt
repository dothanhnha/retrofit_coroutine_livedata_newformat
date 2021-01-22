package com.example.loadmoredemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    lateinit var dataset:List<Item>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bindView(dataset[position])
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(item: Item) {

        }

    }
}