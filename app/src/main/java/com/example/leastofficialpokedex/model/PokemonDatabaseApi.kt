package com.example.leastofficialpokedex.model

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PokemonDatabaseApi {

    @GET("pokemon/")
    fun getNext20(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int = 20):
            Observable<PokemonModel.PokemonListResponse>

    @GET("")
    fun getPokemonData(): Observable<PokemonModel.PokemonData>

    companion object {
        fun create(): PokemonDatabaseApi {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://pokeapi.co/api/v2/")
                .build()

            return retrofit.create(PokemonDatabaseApi::class.java)
        }
    }
}