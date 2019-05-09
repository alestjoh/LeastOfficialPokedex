package com.example.leastofficialpokedex.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.leastofficialpokedex.R
import com.example.leastofficialpokedex.model.PokemonModel
import com.example.leastofficialpokedex.viewModel.PokemonViewModel
import kotlinx.android.synthetic.main.name_recycler_item_layout.view.*

class PokemonNameRecyclerAdapter(
    private val pokemonViewModel: PokemonViewModel,
    private val nameClickListener: NameClickListener):
    RecyclerView.Adapter<PokemonNameRecyclerAdapter.PokemonNameViewHolder>() {

    private val nameList: MutableList<PokemonModel.DatabaseName> =
        listOf<PokemonModel.DatabaseName>().toMutableList()
    private var workingList: List<PokemonModel.DatabaseName>? = null
    private val observer: Observer<List<PokemonModel.DatabaseName>> = Observer {
        if (it.isNotEmpty()) {
            nameList.addAll(it)
            searchString = mySearchString
            notifyDataSetChanged()
            pokemonViewModel.getMoreNames()
        }
    }
    private var mySearchString: String = ""
    var searchString: String
        get() = mySearchString
        set(value) {
            mySearchString = value
            workingList = nameList.filter { it.name.contains(mySearchString) }
            notifyDataSetChanged()
        }

    init {
        pokemonViewModel.startIndex = 0
        pokemonViewModel.nameList.observeForever(observer)
        pokemonViewModel.getMoreNames()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonNameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.name_recycler_item_layout, parent, false)
        return PokemonNameViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonNameViewHolder, position: Int) {
        val name = workingList?.get(position)?.name ?: nameList[position].name
        holder.nameText.text = stripExtraDataFromName(name)
        holder.cardView.setOnClickListener { nameClickListener.onClick(name) }
    }

    /**
     * Method to remove modifiers to the pokemon species
     * like form designations or "medium"
     */
    private fun stripExtraDataFromName(name: String): String {
        return if (name.contains("-"))
            name.substring(0, name.indexOf('-')).capitalize()
        else
            name.capitalize()
    }

    override fun getItemCount(): Int = workingList?.size ?: nameList.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        pokemonViewModel.nameList.removeObserver(observer)
    }

    class PokemonNameViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.tv_name_nameLayout
        val cardView: CardView = view.cardView_nameItem
    }

    interface NameClickListener {
        fun onClick(pokemonName: String)
    }
}