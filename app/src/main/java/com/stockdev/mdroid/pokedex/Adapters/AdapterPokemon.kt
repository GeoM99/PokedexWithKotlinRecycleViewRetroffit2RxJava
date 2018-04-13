package com.stockdev.mdroid.pokedex.Adapters

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import com.stockdev.mdroid.pokedex.Models.PokedexModel
import com.stockdev.mdroid.pokedex.Models.Result
import com.stockdev.mdroid.pokedex.R
import kotlinx.android.synthetic.main.list_pokemons_items.view.*


class AdapterPokemon(var listPokemons : ArrayList<PokedexModel>) : RecyclerView.Adapter<AdapterPokemon.ViewHolder>(){

        class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

              fun onBindItems(pokemons : PokedexModel){
                itemView.textViewPokemonName.text = pokemons.name


              }
        }


  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

    val layoutInfate = LayoutInflater.from(parent.context)
       .inflate(R.layout.list_pokemons_items, parent, false)

    return AdapterPokemon.ViewHolder(layoutInfate)

  }

  override fun getItemCount(): Int {
     return  listPokemons.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val itemPokemons = listPokemons[position]
          holder.onBindItems(itemPokemons)


            fun urlNumber() : Int{
                var urlNumber = itemPokemons.url.split("/")
                    return urlNumber.get(6).toInt()
            }

            Log.d("number ", urlNumber().toString())

      Picasso.get()
              .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${urlNumber()}.png")
              .into(holder.itemView.imageViewPokemonImage)
  }


}