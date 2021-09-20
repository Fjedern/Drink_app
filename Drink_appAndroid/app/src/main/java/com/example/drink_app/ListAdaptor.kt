package com.example.drink_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdaptor (var list : List<Ingredient>) : RecyclerView.Adapter<ListAdaptor.ListViewHolder>() {

    inner class ListViewHolder(itemView : View) :
            RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.inventory_recycler_list, parent, false)

        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemView.apply {
            val tv_ingredient_id: TextView = findViewById(R.id.tv_ingredient_id)
            val tv_ingredient_name: TextView = findViewById(R.id.tv_ingredient_name)

            tv_ingredient_id.text = list[position].id.toString()
            tv_ingredient_name.text = list[position].name

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}