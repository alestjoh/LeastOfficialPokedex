package com.example.leastofficialpokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leastofficialpokedex.R
import com.example.leastofficialpokedex.viewmodel.PokemonViewmodel
import kotlinx.android.synthetic.main.activity_main.*

class LandingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pokemonViewmodel = ViewModelProviders.of(this)
            .get(PokemonViewmodel::class.java)

        recyclerView_names_landing.layoutManager = LinearLayoutManager(this)
        recyclerView_names_landing.adapter = PokemonNameRecyclerAdapter(pokemonViewmodel)
    }
}
