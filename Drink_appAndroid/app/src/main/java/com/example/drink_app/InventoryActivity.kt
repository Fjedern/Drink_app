package com.example.drink_app

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
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

            for (i in list) {
                Log.d("debug", i.isChecked.toString())
                if (i.isChecked) {
                    databaseHandler.deleteIngredient(i.id)
                    updateRecycler(databaseHandler, rv_ingredient_list)
                    i.isChecked = false

                } else {
                    Log.d("deleteFalse", "nothing is checked")
                }
            }
        }

        //hämtar Ingredient name och sätter till EditText-rutan
        btnUpdate.setOnClickListener {
            val list = listAdaptor.list
            for (i in list) {
                if (i.isChecked) {
                    //Log.d("activity", i.name)
                    createPopUp(databaseHandler, rv_ingredient_list, i.name)
                    i.isChecked = false
                    break
                }
            }
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
    private fun createPopUp(db: DatabaseHandler, rv_list: RecyclerView, updateName: String) {

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
            //Log.d("alertPos", "you clicked saved " + newName)
            if (updateName.lowercase() != newNameFromPop.lowercase() && newNameFromPop.trim().length != 0) {
                updateIngredientname(db, rv_list, newNameFromPop, updateName)
                //Log.d("popUp", newNameFromPop)
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
        db: DatabaseHandler,
        rv_list: RecyclerView,
        newNameFromPop: String,
        updateName: String
    ) {
        //Log.d("updateFun", newNameFromPop)
        db.updateIngredient(updateName, newNameFromPop)
        updateRecycler(db, rv_list)
    }


}