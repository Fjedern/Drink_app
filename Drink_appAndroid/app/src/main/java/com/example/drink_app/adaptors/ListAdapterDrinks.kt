package com.example.drink_app.adaptors

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView


import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.drink_app.R
import com.example.drink_app.ShowDrinkRecipe
import com.example.drink_app.network.Drink

class ListAdapterDrinks(val drinkList: List<Drink>) :
    RecyclerView.Adapter<ListAdapterDrinks.ListViewHolder>() {
    inner class ListViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        // makes each card clickable
        init {
            itemView.setOnClickListener {
                val drinkid = drinkList[adapterPosition].idDrink
/*
                Toast.makeText(itemView.getContext(), "DrinkId: " + drinkList[adapterPosition].idDrink, Toast.LENGTH_LONG).show()
*/
                val intent = Intent(itemView.context, ShowDrinkRecipe::class.java)
                intent.putExtra("drinkId", drinkid)
                itemView.context.startActivity(intent)
            }
        }

        fun bindData(drink: Drink) {
            val tv_name = itemView.findViewById<TextView>(R.id.tv_name)
            val iv_image = itemView.findViewById<ImageView>(R.id.iv_image)

            tv_name.text = drink.strDrink
            iv_image.load(drink.strDrinkThumb) {
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.drink_rv_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bindData(drinkList[position])
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }
}