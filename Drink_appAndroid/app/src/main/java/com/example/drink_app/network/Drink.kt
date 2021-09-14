package com.example.drink_app.network

import com.squareup.moshi.Json

data class Drink (
    @Json(name = "idDrink")
    val idDrink : String,
    @Json(name = "strDrink")
    val strDrink : String,
    @Json(name = "strDrinkThumb")
    val strDrinkThumb : String
)

data class DrinkResponse(@Json(name = "results") val result : List<Drink>)