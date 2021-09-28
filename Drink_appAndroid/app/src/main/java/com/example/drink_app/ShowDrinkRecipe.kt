package com.example.drink_app

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
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
        setContentView(R.layout.activity_show_drink_recipie)

        val tv_drink_name: TextView = findViewById(R.id.tv_drink_name)
        val iv_drink_image: ImageView = findViewById(R.id.iv_drink_image)
        val tv_instructions: TextView = findViewById(R.id.tv_instructions)

        //Get tablerows for displaying ingredients
        val tr_1: TableRow = findViewById(R.id.tr_1)
        val tr_2: TableRow = findViewById(R.id.tr_2)
        val tr_3: TableRow = findViewById(R.id.tr_3)

        //List of empty textviews for creating new textviewxs
        val tvIngredients = arrayOfNulls<TextView>(15)
        val rnd = Random()

        val intent = intent
        val drinkId = intent.getStringExtra("drinkId").toString()

        val client = APIClient.apiService.fetchDrinkById(drinkId)

        //Api Call
       client.enqueue(object : retrofit2.Callback<DrinkResponseSpecificDrink>{
            override fun onResponse(call: Call<DrinkResponseSpecificDrink>, response: Response<DrinkResponseSpecificDrink>) {
                if(response.isSuccessful){
                    val result = response.body()?.drinkByName
                    result?.let {
                        tv_drink_name.text = result[0].strDrink

                        iv_drink_image.load(result[0].strDrinkThumb)
                        }
                        val ingredientList: List<String?>? = result?.get(0)?.ingredientList
                        val measureList: List<String?>? = result?.get(0)?.measureList

                        //Create textviews for every ingredient
                        if (ingredientList != null) {
                            for (i in ingredientList.indices) {
                                if(ingredientList[i] == null) break
                                tvIngredients[i] = TextView(this@ShowDrinkRecipe)

                                val color: Int = Color.argb(
                                    255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256)
                                )
                                tvIngredients[i]!!.setTextColor(color)
                                if(measureList?.get(i) != null) {
                                    tvIngredients[i]!!.text = measureList?.get(i) + " " + ingredientList[i]
                                } else {
                                    tvIngredients[i]!!.text = ingredientList[i]
                                }
                                tvIngredients[i]!!.textSize = 15.toFloat()
                                tvIngredients[i]!!.setPadding(10, 10, 10, 10)

                                //Add textviews to tablerows
                                if(i <= 1) tr_1.addView(tvIngredients[i])
                                else if (i in 2..3) tr_2.addView(tvIngredients[i])
                                else if (i in 4..6) tr_3.addView(tvIngredients[i])
                            }
                        }

                        val str = result?.get(0)?.strInstructions
                        val instructionsArray: List<String>? = str?.split(". ")
                        var tempstr = ""
                        if (instructionsArray != null) {
                            for (i in instructionsArray.indices) {
                                tempstr += instructionsArray[i] + "\n\n"
                            }
                            tv_instructions.text = tempstr
                        }
                    }

                }

           override fun onFailure(call: Call<DrinkResponseSpecificDrink>, t: Throwable) {
               Log.e("ShowDrinkRecipe", "Something went wrong: " + t)
           }
        })
    }
}