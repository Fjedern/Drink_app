package com.example.drink_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_inventory: Button = findViewById(R.id.btn_inventory)
        val btn_recepies: Button = findViewById(R.id.btn_recepies)
        val btn_random_drink: Button = findViewById(R.id.btn_random_drink)

        btn_inventory.setOnClickListener {
            val intent = Intent(this, InventoryActivity::class.java)
            startActivity(intent)
        }

        btn_recepies.setOnClickListener {
            // To be removed when not used
            Toast.makeText(this@MainActivity, "You clicked recepies.", Toast.LENGTH_SHORT).show()
        }

        btn_random_drink.setOnClickListener {
            // To be removed when not used
            Toast.makeText(this@MainActivity, "You clicked random drink.", Toast.LENGTH_SHORT)
                .show()
        }
    }
}