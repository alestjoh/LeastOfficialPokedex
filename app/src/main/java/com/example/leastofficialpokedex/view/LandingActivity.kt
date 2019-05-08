package com.example.leastofficialpokedex.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.leastofficialpokedex.R
import com.example.leastofficialpokedex.viewmodel.PokemonViewmodel
import kotlinx.android.synthetic.main.activity_main.*

class LandingActivity : AppCompatActivity() {

    lateinit var pokemonViewmodel: PokemonViewmodel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
