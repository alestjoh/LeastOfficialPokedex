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

    private val nameList: MutableList<PokemonModel.PokemonName> =
        listOf<PokemonModel.PokemonName>().toMutableList()
    private val observer: Observer<List<PokemonModel.PokemonName>> = Observer {
        nameList.addAll(it)
        notifyDataSetChanged()
        pokemonViewModel.getMoreNames()
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
        holder.nameText.text = nameList[position].name
        holder.cardView.setOnClickListener { nameClickListener.onClick(nameList[position].name) }
    }

    override fun getItemCount(): Int = nameList.size

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