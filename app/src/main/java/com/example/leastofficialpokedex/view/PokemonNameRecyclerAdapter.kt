package com.example.leastofficialpokedex.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.leastofficialpokedex.R
import com.example.leastofficialpokedex.model.PokemonModel
import com.example.leastofficialpokedex.viewmodel.PokemonViewmodel
import kotlinx.android.synthetic.main.name_recycler_item_layout.view.*

class PokemonNameRecyclerAdapter(private val pokemonViewmodel: PokemonViewmodel):
    RecyclerView.Adapter<PokemonNameRecyclerAdapter.PokemonNameViewHolder>() {

    private val nameList: MutableList<PokemonModel.PokemonName> =
        listOf<PokemonModel.PokemonName>().toMutableList()
    private val observer: Observer<List<PokemonModel.PokemonName>> = Observer {
        nameList.addAll(it)
        notifyDataSetChanged()
        pokemonViewmodel.getMoreNames()
    }

    init {
        pokemonViewmodel.startIndex = 0
        pokemonViewmodel.nameList.observeForever(observer)
        pokemonViewmodel.getMoreNames()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonNameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.name_recycler_item_layout, parent, false)
        return PokemonNameViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonNameViewHolder, position: Int) {
        holder.nameText.text = nameList[position].name
    }

    override fun getItemCount(): Int = nameList.size

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        pokemonViewmodel.nameList.removeObserver(observer)
    }

    class PokemonNameViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.tv_name_nameLayout
    }
}