package com.example.drink_app.adaptors

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.drink_app.DatabaseHandler
import com.example.drink_app.Ingredient
import com.example.drink_app.InventoryActivity
import com.example.drink_app.R

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
            val checkBox: CheckBox = findViewById(R.id.cb_ingredient)

            tv_ingredient_id.text = list[position].id.toString()
            tv_ingredient_name.text = list[position].name
            //checkBox.text = list[position].isChecked.toString() //SKA TAS BORT SEN

            checkBox.setOnCheckedChangeListener{
                buttonView, isChecked ->
                    checkBoxClick(checkBox, list[position].id)

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun checkBoxClick(checkBox: CheckBox, id: Int){ //return id??

        if (checkBox.isChecked){

            checkBox.id = id //Har nu samma id som Ingredient-Item
            //checkBox.text = true.toString()
            Log.d("adapter", checkBox.id.toString())

            //set ingredient isChecked = True ??


        }

    }

    //fun getIdToDelete(): Int{
        //how to find and check all checkboxes

        //return id
    //}

}
