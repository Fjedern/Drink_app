package com.example.drink_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.drink_app.network.APIClient
import com.example.drink_app.network.DrinkResponseSpecificDrink
import retrofit2.Call
import retrofit2.Response

class ShowDrinkRecipe : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_drink_recipie)

        val tv_drink_name: TextView = findViewById(R.id.tv_drink_name)
        val tv_how_to_do_descripton: TextView = findViewById(R.id.tv_how_to_do_description)
        val iv_drink_image: ImageView = findViewById(R.id.iv_drink_image)

        val intent = intent
        val drinkId = intent.getStringExtra("drinkId").toString()

        val client = APIClient.apiService.fetchDrinkById(drinkId)


       client.enqueue(object : retrofit2.Callback<DrinkResponseSpecificDrink>{
            override fun onResponse(call: Call<DrinkResponseSpecificDrink>, response: Response<DrinkResponseSpecificDrink>) {
                if(response.isSuccessful){
                    //Log.d("1", ""+ response.body())
                    tv_how_to_do_descripton.text = "Inside if"


                    val result = response.body()?.drinkByName
                    result?.let {
                        tv_drink_name.text = result[0].idDrink
                      //  iv_drink_image.load(result[0].strDrinkThumb) {
                          //  transformations(CircleCropTransformation())
                        //}
                    }  /*val result = response.body()?.drinkByName
                    result?.let {
                        tv_drink_name.text = result[0].strDrink
                        iv_drink_image.load(result[0].strDrinkThumb)


                     *//*   val rv_drinks = findViewById<RecyclerView>(R.id.rv_drinks)
                        val listAdapter = ListAdapterDrinks(result)
                        rv_drinks.adapter = listAdapter
                        rv_drinks.layoutManager = StaggeredGridLayoutManager(
                            4, StaggeredGridLayoutManager.VERTICAL
                        )*//*
                    }*/
                }
            }

            override fun onFailure(call: Call<DrinkResponseSpecificDrink>, t: Throwable) {
                Log.e("ShowDrinkRecipe", "Something went wrong: " + t)
                tv_how_to_do_descripton.text = "Fail"
            }

        })

        tv_drink_name.text=drinkId
        Toast.makeText(
            this@ShowDrinkRecipe,
            drinkId,
            Toast.LENGTH_SHORT

        ).show()

    }
}