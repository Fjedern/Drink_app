package com.example.drink_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.drink_app.network.APIClient
import com.example.drink_app.network.DrinkResponse
import retrofit2.Call
import retrofit2.Response

class DrinkList : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_list)

        val client = APIClient.apiService.fetchDrinks("Gin")

        client.enqueue(object : retrofit2.Callback<DrinkResponse>{
            override fun onResponse(call: Call<DrinkResponse>, response: Response<DrinkResponse>) {
                if(response.isSuccessful){
                    Log.d("1", ""+ response.body())

                    val result = response.body()?.drinks
                    result?.let {
                        val rv_drinks = findViewById<RecyclerView>(R.id.rv_drinks)
                        val listAdapter = ListAdapterDrinks(result)
                        rv_drinks.adapter = listAdapter
                        rv_drinks.layoutManager = StaggeredGridLayoutManager(
                            4, StaggeredGridLayoutManager.VERTICAL
                        )
                    }
                }
            }

            override fun onFailure(call: Call<DrinkResponse>, t: Throwable) {
                Log.e("MainActivity", "Something went wrong: " + t)
            }

        })
        Log.d("MainActivity", "test")
    }
}