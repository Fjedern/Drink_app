package com.example.drink_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.drink_app.adaptors.ListAdaptor

class InventoryActivity : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.mi_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.mi_profile -> {
                Toast.makeText(
                    this,
                    "You clicked profile button",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }
            R.id.mi_setting -> {
                Toast.makeText(
                    this,
                    "You clicked settings button",
                    Toast.LENGTH_SHORT
                ).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        val btnWhatCanIMake: Button = findViewById(R.id.btn_what_can_i_make)
        val btnAdd: Button = findViewById(R.id.btn_add)
        val btnDelete: Button = findViewById(R.id.btn_delete)
        val btnUpdate: Button = findViewById(R.id.btn_update)
        val etInput: EditText = findViewById(R.id.et_input)


        //Get and create the Database
        val databaseHandler : DatabaseHandler = DatabaseHandler(this)
        val rv_ingredient_list : RecyclerView = findViewById(R.id.rv_ingredient_list)
        var listAdaptor = ListAdaptor(databaseHandler.viewAll())
        rv_ingredient_list.layoutManager = LinearLayoutManager(this)
        rv_ingredient_list.adapter = listAdaptor


        //BUTTON ONCLICK LISTENERS
        
        btnWhatCanIMake.setOnClickListener {
            val list = listAdaptor.list
            for (i in list) {
                if (i.isChecked) {
                    val intent = Intent(this, DrinkList::class.java)
                    intent.putExtra("ingredientName", i.name)
                    startActivity(intent)
                    break
                }
            }
        }

        //Add ingedient to database and update recycler view
        btnAdd.setOnClickListener {

            if(etInput.getText().toString().trim().length == 0){ //om värdet är tomt eller bara space
                showToast("Nothing was entered, please try again")
                etInput.text.clear()
            }else {
                val ingredient_input: String = etInput.text.toString()
                val success = databaseHandler.addToDataBase(ingredient_input)

                if (success == true) {
                    //showToast(ingredient_input + "was added")
                    updateRecycler(databaseHandler, rv_ingredient_list)
                    etInput.text.clear()
                }
                Log.d("database", success.toString())
                closeKeyBoard()
            }

        }

        btnDelete.setOnClickListener {
            val list = listAdaptor.list

            for (i in list){
                if(i.isChecked){
                    Log.d("activity", i.name)
                    databaseHandler.deleteIngredient(i.id)
                    updateRecycler(databaseHandler, rv_ingredient_list)
                }
            }
        }


        btnUpdate.setOnClickListener {
            // To be removed when not used
            showToast("Update")
        }

    }

    //INVENTORY FUNCTIONS
    private fun updateRecycler(databaseHandler: DatabaseHandler, rv_ingredient_list: RecyclerView) {
        val drinkList = databaseHandler.viewAll()
        var listAdaptor = ListAdaptor(drinkList)
        rv_ingredient_list.adapter = listAdaptor
    }

    private fun readIngredientName(): String {
        val etIngredientsName: EditText = findViewById(R.id.et_input)
        return etIngredientsName.text.toString()
    }



    // TODO To be removed when not used
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