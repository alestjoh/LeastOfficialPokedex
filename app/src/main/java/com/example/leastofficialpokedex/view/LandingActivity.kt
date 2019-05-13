package com.example.leastofficialpokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.leastofficialpokedex.R
import com.example.leastofficialpokedex.viewModel.PokemonViewModel
import com.example.leastofficialpokedex.viewModel.VisionViewModel
import kotlinx.android.synthetic.main.activity_main.*

class LandingActivity : AppCompatActivity(), PokemonNameRecyclerAdapter.NameClickListener {

    companion object {
        const val POKEMON_NAME = "LandingActivity.DatabaseName"
    }

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
        adapter.searchString = ""

        val visionViewModel = ViewModelProviders.of(this)
            .get(VisionViewModel::class.java)
        visionViewModel.message.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        //visionViewModel.visionTest(this)
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
        supportFragmentManager.beginTransaction().remove(fragment!!).commit()
    }

    override fun onPause() {
        super.onPause()
        if (fragment != null) {
            supportFragmentManager.popBackStackImmediate(
                fragment!!.pokemonName,
                FragmentManager.POP_BACK_STACK_INCLUSIVE)
            fragment = null
        }
    }

    inner class WatchSearchString(private val adapter: PokemonNameRecyclerAdapter): TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            adapter.searchString = s.toString()
            if (s.toString() == "") {
                if (fragment != null) {
                    removeFragment()
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }
}
