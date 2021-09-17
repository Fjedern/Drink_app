package com.example.drink_app.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object APIClient {
    private val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    private val moshi by lazy { Moshi.Builder().add(KotlinJsonAdapterFactory()).build() }

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    val apiService : ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    val apiService2: ApiService2 by lazy {
        retrofit.create(ApiService2::class.java)
    }
}

interface ApiService {
    @GET("filter.php")
    fun fetchDrinks(@Query("i") ingredient : String) : Call<DrinkResponse>
}
interface ApiService2 {
    @GET("random.php")
    fun fetchRandomDrinks(@Query("i") ingredient: String): Call<DrinkResponse>
}