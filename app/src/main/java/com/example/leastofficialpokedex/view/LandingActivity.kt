package com.example.leastofficialpokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leastofficialpokedex.R
import com.example.leastofficialpokedex.viewModel.PokemonViewModel
import kotlinx.android.synthetic.main.activity_main.*

class LandingActivity : AppCompatActivity(), PokemonNameRecyclerAdapter.NameClickListener {

    private var fragment: PokemonDetailsFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pokemonViewModel = ViewModelProviders.of(this)
            .get(PokemonViewModel::class.java)

        val adapter = PokemonNameRecyclerAdapter(pokemonViewModel, this)
        recyclerView_names_landing.layoutManager = LinearLayoutManager(this)
        recyclerView_names_landing.adapter = adapter

        et_search_landing.addTextChangedListener(WatchSearchString(adapter))
    }

    override fun onClick(pokemonName: String) {
        if (fragment != null) {
            removeFragment()
        }

        fragment = PokemonDetailsFragment(pokemonName)
        supportFragmentManager.beginTransaction()
            .add(R.id.frameLayout_fragmentHolder_landing, fragment!!)
            .addToBackStack(pokemonName)
            .commit()
    }

    private fun removeFragment() {
        supportFragmentManager.beginTransaction()
            .remove(fragment!!)
            .commit()
    }

    class WatchSearchString(val adapter: PokemonNameRecyclerAdapter): TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            adapter.searchString = s.toString()
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }
}
