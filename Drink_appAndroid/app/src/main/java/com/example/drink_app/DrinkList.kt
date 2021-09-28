package com.example.drink_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.drink_app.adaptors.ListAdapterDrinks
import com.example.drink_app.network.APIClient
import com.example.drink_app.network.DrinkResponse
import retrofit2.Call
import retrofit2.Response

class DrinkList : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    //Menu
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
        setContentView(R.layout.activity_drink_list)

        // search by ingredient
        val intent = intent
        val ingredientName = intent.getStringExtra("ingredientName")
        val client = APIClient.apiService.fetchDrinks(ingredientName.toString())

        //API Call
        client.enqueue(object : retrofit2.Callback<DrinkResponse> {
            override fun onResponse(call: Call<DrinkResponse>, response: Response<DrinkResponse>) {
                if (response.isSuccessful) {
                    Log.d("1", "" + response.body())

                    val result = response.body()?.drinks
                    result?.let {
                        //Populate recyclerview
                        val rv_drinks = findViewById<RecyclerView>(R.id.rv_drinks)
                        val listAdapter = ListAdapterDrinks(result)
                        rv_drinks.adapter = listAdapter

                        rv_drinks.layoutManager = StaggeredGridLayoutManager(
                            4, StaggeredGridLayoutManager.VERTICAL
                        )
                    }
                }
            }

            //If Api Call fails
            override fun onFailure(call: Call<DrinkResponse>, t: Throwable) {
                // To be removed?
                showToast("Nothing with ingredient " + ingredientName.toString() + " was found!")
                Log.e("MainActivity", "Something went wrong: " + t)
            }

        })
        Log.d("MainActivity", "test")
    }

    // To be removed when not used
    private fun showToast(message: String) {

        Toast.makeText(
            this@DrinkList,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}