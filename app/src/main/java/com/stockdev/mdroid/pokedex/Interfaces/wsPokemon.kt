package com.stockdev.mdroid.pokedex.Interfaces

import com.stockdev.mdroid.pokedex.Models.PokedexModel
import com.stockdev.mdroid.pokedex.Models.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface wsPokemon {

  @GET("pokemon")
      fun getPokemons(@Query("limit") limit : Int, @Query("offset") offset : Int ) : Observable<Result>
}