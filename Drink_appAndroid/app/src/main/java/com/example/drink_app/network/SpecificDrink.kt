package com.example.drink_app.network

import com.squareup.moshi.Json

data class SpecificDrink(
    @Json(name = "idDrink")
    val idDrink: String?,
    @Json(name = "strDrink")
    val strDrink: String?,
    @Json(name = "strDrinkAlternate")
    val strDrinkAlternate: String?,
    @Json(name = "strTags")
    val strTags: String?,
    @Json(name = "strVideo")
    val strVideo: String?,
    @Json(name = "strCategory")
    val strCategory: String?,
    @Json(name = "strIBA")
    val strIBA: String?,
    @Json(name = "strAlcoholic")
    val strAlcoholic: String?,
    @Json(name = "strGlass")
    val strGlass: String?,
    @Json(name = "strInstructions")
    val strInstructions: String?,
    @Json(name = "strInstructionsES")
    val strInstructionsES: String?,
    @Json(name = "strInstructionsDE")
    val strInstructionsDE: String?,
    @Json(name = "strInstructionsFR")
    val strInstructionsFR: String?,
    @Json(name = "strInstructionsIT")
    val strInstructionsIT: String?,
    @Json(name = "strInstructionsZH-HANS")
    val strInstructionsZH_HANS: String?,
    @Json(name = "strInstructionsZH-HANT")
    val strInstructionsZH_HANT: String?,
    @Json(name = "strDrinkThumb")
    val strDrinkThumb: String?,
    @Json(name = "strIngredient1")
    val strIngredient1: String?,
    @Json(name = "strIngredient2")
    val strIngredient2: String?,
    @Json(name = "strIngredient3")
    val strIngredient3: String?,
    @Json(name = "strIngredient4")
    val strIngredient4: String?,
    @Json(name = "strIngredient5")
    val strIngredient5: String?,
    @Json(name = "strIngredient6")
    val strIngredient6: String?,
    @Json(name = "strIngredient7")
    val strIngredient7: String?,
    @Json(name = "strIngredient8")
    val strIngredient8: String?,
    @Json(name = "strIngredient9")
    val strIngredient9: String?,
    @Json(name = "strIngredient10")
    val strIngredient10: String?,
    @Json(name = "strIngredient11")
    val strIngredient11: String?,
    @Json(name = "strIngredient12")
    val strIngredient12: String?,
    @Json(name = "strIngredient13")
    val strIngredient13: String?,
    @Json(name = "strIngredient14")
    val strIngredient14: String?,
    @Json(name = "strIngredient15")
    val strIngredient15: String?,
    @Json(name = "strMeasure1")
    val strMeasure1: String?,
    @Json(name = "strMeasure2")
    val strMeasure2: String?,
    @Json(name = "strMeasure3")
    val strMeasure3: String?,
    @Json(name = "strMeasure4")
    val strMeasure4: String?,
    @Json(name = "strMeasure5")
    val strMeasure5: String?,
    @Json(name = "strMeasure6")
    val strMeasure6: String?,
    @Json(name = "strMeasure7")
    val strMeasure7: String?,
    @Json(name = "strMeasure8")
    val strMeasure8: String?,
    @Json(name = "strMeasure9")
    val strMeasure9: String?,
    @Json(name = "strMeasure10")
    val strMeasure10: String?,
    @Json(name = "strMeasure11")
    val strMeasure11: String?,
    @Json(name = "strMeasure12")
    val strMeasure12: String?,
    @Json(name = "strMeasure13")
    val strMeasure13: String?,
    @Json(name = "strMeasure14")
    val strMeasure14: String?,
    @Json(name = "strMeasure15")
    val strMeasure15: String?,
    @Json(name = "strImageSource")
    val strImageSource: String?,
    @Json(name = "strImageAttribution")
    val strImageAttribution: String?,
    @Json(name = "strCreativeCommonsConfirmed")
    val strCreativeCommonsConfirmed: String?,
    @Json(name = "dateModified")
    val dateModified: String?,

    val ingredientList: List<String?> = mutableListOf(strIngredient1, strIngredient2, strIngredient3, strIngredient4, strIngredient5, strIngredient6, strIngredient7, strIngredient8, strIngredient9, strIngredient10, strIngredient11, strIngredient12, strIngredient13, strIngredient14, strIngredient15),
    val measureList: List<String?> = mutableListOf(strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5, strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10, strMeasure11, strMeasure12, strMeasure13, strMeasure14, strMeasure15)

)

data class DrinkResponseSpecificDrink(@Json(name = "drinks") val drinkByName : List<SpecificDrink>)

