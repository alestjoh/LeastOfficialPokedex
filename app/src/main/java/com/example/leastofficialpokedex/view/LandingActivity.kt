package com.example.leastofficialpokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.leastofficialpokedex.R
import com.example.leastofficialpokedex.viewmodel.PokemonViewmodel
import kotlinx.android.synthetic.main.activity_main.*

class LandingActivity : AppCompatActivity() {

    lateinit var pokemonViewmodel: PokemonViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pokemonViewmodel = ViewModelProviders.of(this)
            .get(PokemonViewmodel::class.java)
//        pokemonViewmodel.nameList.observe(this, Observer {
//                list -> tv_defaultText_landing.text = list[0].name
//        })
//        pokemonViewmodel.getMoreNames()
//        pokemonViewmodel.pokemonData.observe(this, Observer {
//            data -> tv_defaultText_landing.text = data.sprites.front_default
//        })
//        pokemonViewmodel.getData("bulbasaur")
    }
}
