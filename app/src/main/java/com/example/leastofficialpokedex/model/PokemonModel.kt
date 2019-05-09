package com.example.leastofficialpokedex.model

object PokemonModel {
    data class PokemonData(val name: String, val sprites: Sprites)
    data class Sprites(val front_default: String)

    data class PokemonListResponse(val results: List<DatabaseName>, val species: Species)
    data class DatabaseName(val name: String)
    data class Species(val name: String)
}