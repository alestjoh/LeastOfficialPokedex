package com.example.leastofficialpokedex.view


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.leastofficialpokedex.R
import com.example.leastofficialpokedex.viewModel.PokemonViewModel
import com.example.leastofficialpokedex.viewModel.TwitterViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_pokemon_details.*
import java.lang.Exception

class PokemonDetailsFragment(val pokemonName: String) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        observePokemonViewModel()
        observeTwitterViewModel()

        return inflater.inflate(R.layout.fragment_pokemon_details, container, false)
    }

    private fun observeTwitterViewModel() {
        val twitterViewModel = ViewModelProviders.of(this)
            .get(TwitterViewModel::class.java)

        twitterViewModel.statuses.observe(this, Observer {
            recyclerView_detailsFragment.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

            recyclerView_detailsFragment.adapter = TweetRecyclerAdapter(it)
        })

        twitterViewModel.getStatuses(pokemonName)
    }

    private fun observePokemonViewModel() {
        val pokemonViewModel = ViewModelProviders.of(this)
            .get(PokemonViewModel::class.java)

        pokemonViewModel.pokemonData.observe(this, Observer {

            Picasso.get().load(it.sprites.front_default).into(
                iv_pokemon_detailsFragment,
                ImageLoadCallback(progressBar_imageLoading_detailsFragment, this.context!!)
            )

            iv_pokemon_detailsFragment.visibility = View.VISIBLE
            tv_name_detailsFragment.text = it.species.name.capitalize()
        })
        pokemonViewModel.getData(pokemonName)
    }

    internal class ImageLoadCallback(
        private val progressBar: ProgressBar,
        private val context: Context): Callback {
        override fun onSuccess() {
            progressBar.visibility = View.GONE
        }

        override fun onError(e: Exception?) {
            Toast.makeText(context, "Error loading image", Toast.LENGTH_LONG).show()
        }
    }
}
