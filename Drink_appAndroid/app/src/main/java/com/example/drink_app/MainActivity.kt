package com.example.drink_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.drink_app.network.APIClient
import com.example.drink_app.network.DrinkResponse
import retrofit2.Call
import retrofit2.Response

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import coil.load
import coil.transform.CircleCropTransformation

class MainActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //no functionality
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getRandomDrink()

        val btn_inventory: Button = findViewById(R.id.btn_inventory)
        val btn_recepies: Button = findViewById(R.id.btn_recepies)
        val btn_random_drink: Button = findViewById(R.id.btn_random_drink)
        val iv_random_image: ImageView = findViewById(R.id.iv_random_image)

        btn_inventory.setOnClickListener {
            val intent = Intent(this, InventoryActivity::class.java)
            startActivity(intent)
        }

        btn_recepies.setOnClickListener {
            // To be removed when not used
            Toast.makeText(this@MainActivity, "You clicked recepies.", Toast.LENGTH_SHORT).show()
        }

        btn_random_drink.setOnClickListener {
            getRandomDrink()
        }

        iv_random_image.setOnClickListener {
            val tv_drink_id :TextView = findViewById(R.id.tv_drink_id)
            val intent = Intent(this, ShowDrinkRecipe::class.java)
            intent.putExtra("drinkId", tv_drink_id.text)
            startActivity(intent)

        }
    }

    private fun getRandomDrink() {
        val tv_random_drink_name: TextView =
            findViewById(R.id.tv_random_drink_name)
        val iv_random_image: ImageView =
            findViewById(R.id.iv_random_image)
        val tv_drink_id :TextView = findViewById(R.id.tv_drink_id)

        val client = APIClient.apiService.fetchRandomDrinks("")

        client.enqueue(object : retrofit2.Callback<DrinkResponse> {
            override fun onResponse(
                call: Call<DrinkResponse>,
                response: Response<DrinkResponse>
            ) {
                if (response.isSuccessful) {
                    //Log.d("1", "" + response.body())

                    val result = response.body()?.drinks
                    result?.let {
                        tv_drink_id.text = result[0].idDrink
                        tv_random_drink_name.text = result[0].strDrink
                        iv_random_image.load(result[0].strDrinkThumb) {
                            transformations(CircleCropTransformation())
                        }
                    }
                }
            }

            override fun onFailure(call: Call<DrinkResponse>, t: Throwable) {
                Log.e("MainActivity", "Something went wrong: " + t)
            }
        })
    }
}

