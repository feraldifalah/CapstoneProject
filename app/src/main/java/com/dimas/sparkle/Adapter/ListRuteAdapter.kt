package com.dimas.sparkle.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.dimas.sparkle.Model.Nomor
import com.dimas.sparkle.R
import java.util.ArrayList

class ListRuteAdapter(private val listRute: ArrayList<Nomor>) : RecyclerView.Adapter<ListRuteAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_rute, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listRute.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Nomor)
    }

    override fun onBindViewHolder(holder: ListRuteAdapter.ListViewHolder, position: Int) {
        val (name, description, photo) = listRute[position]
        holder.imgPhoto.setImageResource(photo)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listRute[holder.adapterPosition])
        }
    }
}