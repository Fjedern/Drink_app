package com.example.drink_app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import coil.load
import com.example.drink_app.network.APIClient
import com.example.drink_app.network.DrinkResponseSpecificDrink
import retrofit2.Call
import retrofit2.Response
import java.util.*

class ShowDrinkRecipe : AppCompatActivity() {
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //no functionality yet
        return true
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_drink_recipie)

        val tv_drink_name: TextView = findViewById(R.id.tv_drink_name)
        val tv_how_to_do_descripton: TextView = findViewById(R.id.tv_how_to_do_description)
        val iv_drink_image: ImageView = findViewById(R.id.iv_drink_image)

        val LL_ingredients: LinearLayout = findViewById(R.id.LL_ingredients)
        val LL_instructions: LinearLayout = findViewById(R.id.LL_instructions)

        val tvIngredients = arrayOfNulls<TextView>(15) //For creating textViews
        val tvInstructions = arrayOfNulls<TextView>(20)
        val rnd = Random()

        val intent = intent
        val drinkId = intent.getStringExtra("drinkId").toString()

        val client = APIClient.apiService.fetchDrinkById(drinkId)


       client.enqueue(object : retrofit2.Callback<DrinkResponseSpecificDrink>{
            override fun onResponse(call: Call<DrinkResponseSpecificDrink>, response: Response<DrinkResponseSpecificDrink>) {
                if(response.isSuccessful){
                    //Log.d("1", ""+ response.body())
                    //tv_how_to_do_descripton.text = "Inside if"


                    val result = response.body()?.drinkByName
                    result?.let {
                        tv_drink_name.text = result[0].strDrink

                        iv_drink_image.load(result[0].strDrinkThumb)
                        }

                        //tv_how_to_do_descripton.text = result?.get(0)?.ingredientList?.size.toString()
                        val ingredientList: List<String?>? = result?.get(0)?.ingredientList
                        val measureList: List<String?>? = result?.get(0)?.measureList

                        if (ingredientList != null) {
                            for (i in ingredientList.indices) {
                                tvIngredients[i] = TextView(this@ShowDrinkRecipe)

                                val color: Int = Color.argb(
                                    255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)
                                )
                                tvIngredients[i]!!.setTextColor(color)
                                tvIngredients[i]!!.text = measureList?.get(i) + " " + ingredientList[i]
                                tvIngredients[i]!!.textSize = 15.toFloat()
                                tvIngredients[i]!!.setPadding(10, 10, 10, 10)
                                LL_ingredients.addView(tvIngredients[i])
                            }
                        }

                        /*val str = result?.get(0)?.strInstructions
                        val instructionsArray: List<String>? = str?.split(".")


                        for (i in instructionsArray?.indices!!) {
                            tvInstructions[i] = TextView(this@ShowDrinkRecipe)

                            tvInstructions[i]!!.text = instructionsArray[i]
                            tvInstructions[i]!!.textSize = 15.toFloat()
                            //tvInstructions[i]!!.setPadding(10, 10, 10, 10)
                            LL_instructions.addView(tvInstructions[i])
                        }*/
                    }

                }

           override fun onFailure(call: Call<DrinkResponseSpecificDrink>, t: Throwable) {
              // TODO("Not yet implemented")
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