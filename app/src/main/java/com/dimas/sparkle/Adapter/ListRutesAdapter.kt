package com.dimas.sparkle.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dimas.sparkle.Model.Nomor
import com.dimas.sparkle.R
import java.util.ArrayList

class ListRutesAdapter(private val listRutes: ArrayList<Nomor>) : RecyclerView.Adapter<ListRutesAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.items_row_rutes, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listRutes.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        var tvDescription: TextView = itemView.findViewById(R.id.tv_item_description)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Nomor)
    }

    override fun onBindViewHolder(holder: ListRutesAdapter.ListViewHolder, position: Int) {
        val (name, description, photo) = listRutes[position]
        holder.tvName.text = name
        holder.tvDescription.text = description
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listRutes[holder.adapterPosition])
        }
    }
}