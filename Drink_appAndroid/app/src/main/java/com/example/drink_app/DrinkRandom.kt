package com.example.drink_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.drink_app.network.APIClient
import com.example.drink_app.network.DrinkResponse
import retrofit2.Call
import retrofit2.Response

class DrinkRandom : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_random)



        val client = APIClient.apiService2.fetchRandomDrinks("")




        client.enqueue(object : retrofit2.Callback<DrinkResponse> {
            override fun onResponse(
                call: Call<DrinkResponse>,
                response: Response<DrinkResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("1", "" + response.body())

                    val result = response.body()?.drinks
                    result?.let {
                        val rv_random_drink = findViewById<RecyclerView>(R.id.rv_random_drink)
                        val listAdapter = ListAdapterDrinks(result)
                        rv_random_drink.adapter = listAdapter
                        rv_random_drink.layoutManager = StaggeredGridLayoutManager(
                            1, StaggeredGridLayoutManager.VERTICAL
                        )
                    }
                }
            }

            override fun onFailure(call: Call<DrinkResponse>, t: Throwable) {

                // To be removed?
                showToast("Nothing was found!")

                Log.e("MainActivity", "Something went wrong: " + t)
            }

        })
        Log.d("MainActivity", "test")
    }
    // To be removed when not used
    private fun showToast(message: String) {

        Toast.makeText(
            this@DrinkRandom,
            message ,
            Toast.LENGTH_SHORT
        ).show()
    }
}