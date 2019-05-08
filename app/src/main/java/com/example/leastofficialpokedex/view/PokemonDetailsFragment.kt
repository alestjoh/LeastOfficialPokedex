package com.example.leastofficialpokedex.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.leastofficialpokedex.R
import com.example.leastofficialpokedex.viewModel.PokemonViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_pokemon_details.*

class PokemonDetailsFragment(private val pokemonName: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel: PokemonViewModel = ViewModelProviders
            .of(this)
            .get(PokemonViewModel::class.java)
        viewModel.pokemonData.observe(this, Observer {
            Picasso.get().load(it.sprites.front_default).into(iv_pokemon_detailsFragment)
        })
        viewModel.getData(pokemonName)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_details, container, false)
    }
}
