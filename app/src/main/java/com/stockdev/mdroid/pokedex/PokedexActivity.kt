package com.stockdev.mdroid.pokedex

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.stockdev.mdroid.pokedex.Adapters.AdapterPokemon
import com.stockdev.mdroid.pokedex.Interfaces.wsPokemon
import com.stockdev.mdroid.pokedex.R.id.recycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_pokedex.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class PokedexActivity : AppCompatActivity() {

  private val URL_POKEMON : String = "https://pokeapi.co/api/v2/"
  private var offset = 0
  private var start : Boolean = true



  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_pokedex)



    val petitionCreate = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(URL_POKEMON)
            .build()
    start = true
    getPokemons(petitionCreate, offset)


    val layoutManager =  GridLayoutManager(this, 3)
      recycle.layoutManager = layoutManager
      recycle.hasFixedSize()
      recycle.addOnScrollListener(object : RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
          super.onScrolled(recyclerView, dx, dy)





          if (dy > 0){

            val visibleItem : Int = layoutManager.childCount
            val totalItemCount : Int = layoutManager.itemCount
            val pastItemCount =  layoutManager.findFirstVisibleItemPosition()

              if (start){
                if((visibleItem + pastItemCount ) >= totalItemCount){
                  start = false
                    offset += 20
                      Log.d("scroll", start.toString())
                        getPokemons(petitionCreate, offset)
                }
                }

          }
        }
      })

  }

  fun getPokemons(petitionCreate : Retrofit , offset : Int){

    val petitionPokemon = petitionCreate.create(wsPokemon::class.java)
    val response = petitionPokemon.getPokemons(20, offset)
    response
       .observeOn(AndroidSchedulers.mainThread())
       .subscribeOn(Schedulers.io())
       .subscribe {
         for (i in it.results){
           Log.d("response", i.name + i.url)
         }
          start = true
           recycle.adapter = AdapterPokemon(it.results)
       }
  }



}
