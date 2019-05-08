package com.example.leastofficialpokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leastofficialpokedex.R
import com.example.leastofficialpokedex.viewModel.PokemonViewModel
import kotlinx.android.synthetic.main.activity_main.*

class LandingActivity : AppCompatActivity(), PokemonNameRecyclerAdapter.NameClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pokemonViewModel = ViewModelProviders.of(this)
            .get(PokemonViewModel::class.java)

        recyclerView_names_landing.layoutManager = LinearLayoutManager(this)
        recyclerView_names_landing.adapter =
            PokemonNameRecyclerAdapter(pokemonViewModel, this)
    }

    override fun onClick(pokemonName: String) {
        val fragment = PokemonDetailsFragment(pokemonName)
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout_fragmentHolder_landing, fragment)
            .commit()
    }
}
