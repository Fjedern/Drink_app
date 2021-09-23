package com.example.drink_app

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
        val tv_instructions: TextView = findViewById(R.id.tv_instructions)

        val tr_1: TableRow = findViewById(R.id.tr_1)
        val tr_2: TableRow = findViewById(R.id.tr_2)
        val tr_3: TableRow = findViewById(R.id.tr_3)

        val tvIngredients = arrayOfNulls<TextView>(15) //For creating textViews
        val rnd = Random()

        val intent = intent
        val drinkId = intent.getStringExtra("drinkId").toString()

        val client = APIClient.apiService.fetchDrinkById(drinkId)


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
              // TODO("Not yet implemented")
               Log.e("ShowDrinkRecipe", "Something went wrong: " + t)
               tv_how_to_do_descripton.text = "Fail"
           }
        })

        /*tv_drink_name.text=drinkId
        Toast.makeText(
            this@ShowDrinkRecipe,
            drinkId,
            Toast.LENGTH_SHORT

        ).show()*/

    }
}