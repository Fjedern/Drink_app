package com.example.drink_app

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InventoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        val btnWhatCanIMake: Button = findViewById(R.id.btn_what_can_i_make)
        val btnAdd: Button = findViewById(R.id.btn_add)
        val btnDelete: Button = findViewById(R.id.btn_delete)
        val btnUpdate: Button = findViewById(R.id.btn_update)
        val btnReturnToMain: Button = findViewById(R.id.btn_return_to_main)
        val etInput: EditText = findViewById(R.id.et_input)

        //Get and create the Database
        val databaseHandler : DatabaseHandler = DatabaseHandler(this)
        val rv_ingredient_list : RecyclerView = findViewById(R.id.rv_ingredient_list)
        val listAdaptor = ListAdaptor(databaseHandler.viewAll())
        rv_ingredient_list.layoutManager = LinearLayoutManager(this)

        rv_ingredient_list.adapter = listAdaptor

        //BUTTON ONCLICK LISTENERS
        btnReturnToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnWhatCanIMake.setOnClickListener {
            // To be change to variable from DB
            val etSearchIngredient: EditText = findViewById(R.id.et_search_ingredient)

            if (etSearchIngredient.text.toString() != "") {
                val intent = Intent(this, DrinkList::class.java)
                intent.putExtra("ingredientName", etSearchIngredient.text.toString())
                startActivity(intent)
            } else {
                showErrorToast("You must write an ingredient!")
            }

        }

        btnAdd.setOnClickListener {
            // To be removed when not used
            showToast("Add")
        }

        btnDelete.setOnClickListener {
            // To be removed when not used
            showToast("Delete")
        }

        btnUpdate.setOnClickListener {
            // To be removed when not used
            showToast("Update")
        }

    }

    //INVENTORY FUNCTIONS
    private fun readIngredientName(): String {
        val etIngredientsName: EditText = findViewById(R.id.et_input)
        return etIngredientsName.text.toString()
    }

    private fun showErrorToast(errorMessage : String) {
        closeKeyBoard()
        Toast.makeText(
            this@InventoryActivity,
            errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }


    // To be removed when not used
    private fun showToast(btnName: String) {
        closeKeyBoard()
        Toast.makeText(
            this@InventoryActivity,
            "You clicked " + btnName + "\n and ingredient is " + readIngredientName(),
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

    }

}