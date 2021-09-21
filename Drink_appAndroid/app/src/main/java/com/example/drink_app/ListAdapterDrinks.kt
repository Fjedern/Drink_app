package com.example.drink_app

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity


import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import coil.transform.*
import coil.transform.CircleCropTransformation
import com.example.drink_app.network.APIClient
import com.example.drink_app.network.Drink
import com.example.drink_app.network.DrinkResponse
import retrofit2.Call
import retrofit2.Response

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
                val tv_name = itemView.findViewById<TextView>(R.id.tv_name)
                val intent = Intent(itemView.context, ShowDrinkRecipe::class.java)
                intent.putExtra("drinkId", drinkid)
/*
                intent.putExtra("drinkName", tv_name.text.toString())
*/
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
    ): ListAdapterDrinks.ListViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.drink_rv_list, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListAdapterDrinks.ListViewHolder, position: Int) {
        holder.bindData(drinkList[position])
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }
}