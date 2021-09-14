package com.example.drink_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.drink_app.network.APIClient
import com.example.drink_app.network.DrinkResponse
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client = APIClient.apiService.fetchCharacters("Gin")

        client.enqueue(object : retrofit2.Callback<DrinkResponse>{
            override fun onResponse(call: Call<DrinkResponse>, response: Response<DrinkResponse>) {
                if(response.isSuccessful){
                    Log.d("1", ""+ response.body())
                }
            }

            override fun onFailure(call: Call<DrinkResponse>, t: Throwable) {
                Log.e("MainActivity", "Something went wrong: " + t)
            }

        })
    }
}