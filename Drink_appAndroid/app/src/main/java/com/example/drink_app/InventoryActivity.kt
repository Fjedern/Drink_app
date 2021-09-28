package com.example.drink_app

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
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
        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        val rv_ingredient_list: RecyclerView = findViewById(R.id.rv_ingredient_list)
        var ingredientList = databaseHandler.viewAll()
        //  var listAdaptor = ListAdaptor(databaseHandler.viewAll())
        var listAdaptor = ListAdaptor(ingredientList)
        rv_ingredient_list.layoutManager = LinearLayoutManager(this)
        rv_ingredient_list.adapter = listAdaptor


        //BUTTON ONCLICK LISTENERS

        btnWhatCanIMake.setOnClickListener {
            for (i in ingredientList) {
                if (i.isChecked) {
                    val intent = Intent(this, DrinkList::class.java)
                    intent.putExtra("ingredientName", i.name)
                    startActivity(intent)
                    break
                }
            }
        }

        //Add ingredient to database and update recycler view
        btnAdd.setOnClickListener {

            if (etInput.getText().toString()
                    .trim().length == 0
            ) { //om värdet är tomt eller bara space
                showErrorToast("Nothing was entered, please try again")
                etInput.text.clear()
            } else {
                val ingredient_input: String = etInput.text.toString()
                val success = databaseHandler.addToDataBase(ingredient_input)

                if (success == true) {
                    val newIngredient =
                        Ingredient(ingredientList.last().id + 1, ingredient_input, false)
                    ingredientList.add(newIngredient)
                    updateRecycler(ingredientList, rv_ingredient_list)
                    etInput.text.clear()
                }
                closeKeyBoard()
            }

        }

        btnDelete.setOnClickListener {
            // Create a new list that contains the elements that will be removed
            val to_be_delete = getCheckedItem(ingredientList)
            if (to_be_delete.size != 0) {
                for (i in to_be_delete) {
                    databaseHandler.deleteIngredient(i.id)
                    val number = i.id
                    val index = findIndex(ingredientList, number)
                    if (index != -1) {
                        ingredientList.removeAt(index)
                    }
                }
            } else {
                showErrorToast("Nothing deleted, no item chosen!")
            }
            updateRecycler(ingredientList, rv_ingredient_list)
        }

        //hämtar Ingredient name och sätter till EditText-rutan
        btnUpdate.setOnClickListener {
            val to_be_updated = getCheckedItem(ingredientList)
            if (to_be_updated.size > 0) {
                for (i in to_be_updated) {
                    val index = findIndex(ingredientList, i.id)
                    if (index != -1) {
                        createPopUp(
                            ingredientList,
                            i.name,
                            databaseHandler,
                            index,
                            rv_ingredient_list
                        )
                    }
                }
            }else {
                showErrorToast("Nothing updated, no item chosen!")
            }

        }

    }
    fun getCheckedItem(ingredientList: List<Ingredient>):List<Ingredient>{
        var itemChecked = mutableListOf<Ingredient>()
        for (i in ingredientList) {
            if (i.isChecked) {
                val item_to_update =
                    Ingredient(i.id, i.name, i.isChecked)
                itemChecked.add(item_to_update)
            }
        }
        return itemChecked
    }

    fun findIndex(ingredierntList: List<Ingredient>, item: Int): Int {

        for (i in ingredierntList.indices) {
            if (ingredierntList[i].id == item) {
                return i
            }
        }
        return -1
    }

    //INVENTORY FUNCTIONS

    private fun updateRecycler(
        ingredierntList: List<Ingredient>,
        rvIngredientList: RecyclerView
    ) {
        rvIngredientList.adapter = ListAdaptor(ingredierntList)
    }


    private fun readIngredientName(): String {
        val etIngredientsName: EditText = findViewById(R.id.et_input)
        return etIngredientsName.text.toString()
    }


    private fun showErrorToast(errorMessage: String) {
        closeKeyBoard()
        Toast.makeText(
            this@InventoryActivity,
            errorMessage,
            Toast.LENGTH_SHORT
        ).show()
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

    //Created AlertDialog for edit Ingredient name
    private fun createPopUp(
        ingredierntList: List<Ingredient>,
        updateName: String,
        databaseHandler: DatabaseHandler,
        index: Int,
        rv_ingredient_list: RecyclerView
    ) {

        val popupAlert: AlertDialog.Builder = AlertDialog.Builder(this)
        val editText = EditText(this)
        var newNameFromPop: String
        editText.setText(updateName)
        popupAlert.setTitle("Update Ingredient")
        popupAlert.setView(editText)

        val layout: LinearLayout = LinearLayout(this)
        layout.setOrientation(LinearLayout.VERTICAL)
        layout.addView(editText)
        popupAlert.setView(layout)

        popupAlert.setPositiveButton("Save", DialogInterface.OnClickListener { dialogInterface, i ->
            newNameFromPop = editText.text.toString()
            if (updateName.lowercase() != newNameFromPop.lowercase() && newNameFromPop.trim().length != 0) {
                updateIngredientname(
                    ingredierntList,
                    newNameFromPop,
                    updateName,
                    databaseHandler,
                    index,
                    rv_ingredient_list
                )
            }
        })

        popupAlert.setNegativeButton(
            "Cancel",
            DialogInterface.OnClickListener { dialogInterface, i ->

                dialogInterface.dismiss() //finish() avslutar activity, dismiss avslutar alertDialog
            })


        val alertDialog: AlertDialog = popupAlert.create()
        alertDialog.show()

    }

    private fun updateIngredientname(
        ingredientList: List<Ingredient>,
        newNameFromPop: String,
        updateName: String,
        databaseHandler: DatabaseHandler,
        index: Int,
        rv_ingredient_list: RecyclerView
    ) {
        databaseHandler.updateIngredient(updateName, newNameFromPop)
        ingredientList[index].name = newNameFromPop
        ingredientList[index].isChecked = false
        updateRecycler(ingredientList, rv_ingredient_list)

    }
}