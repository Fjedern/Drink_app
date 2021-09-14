package com.example.drink_app

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class InventoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)

        val btnWhatCanIMake: Button = findViewById(R.id.btn_what_can_i_make)
        val btnAdd: Button = findViewById(R.id.btn_add)
        val btnDelete: Button = findViewById(R.id.btn_delete)
        val btnUpdate: Button = findViewById(R.id.btn_update)
        val btnReturnToMain: Button = findViewById(R.id.btn_return_to_main)


        btnReturnToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnWhatCanIMake.setOnClickListener {
            // To be removed when not used
            showToast("What can I make")
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

    private fun readIngredientName(): String {
        val etIngredientsName: EditText = findViewById(R.id.et_ingredients_name)
        return etIngredientsName.text.toString()
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