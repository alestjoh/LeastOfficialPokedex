package com.example.leastofficialpokedex.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leastofficialpokedex.model.PokemonDatabaseApi
import com.example.leastofficialpokedex.model.PokemonModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PokemonViewModel: ViewModel() {
    companion object {
        const val MAX_POKEMON = 802
        const val BUFFER_SIZE = 20
        val api: PokemonDatabaseApi by lazy { PokemonDatabaseApi.create() }
        var disposable: Disposable? = null
    }
    var startIndex = 0

    private val myNameList: MutableLiveData<List<PokemonModel.DatabaseName>> = MutableLiveData()
    val nameList: LiveData<List<PokemonModel.DatabaseName>> get() = myNameList

    private val myPokemonData: MutableLiveData<PokemonModel.PokemonData> = MutableLiveData()
    val pokemonData: LiveData<PokemonModel.PokemonData> get() = myPokemonData

    fun getMoreNames() {
        val limit = if (startIndex + BUFFER_SIZE <= MAX_POKEMON) BUFFER_SIZE
            else MAX_POKEMON - startIndex

        if (limit > 0) {
            disposable?.dispose()
            disposable = api.getNext20(startIndex, limit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result: PokemonModel.PokemonListResponse ->
                    myNameList.postValue(result.results)
                    startIndex += limit
                }
        } else {
            myNameList.postValue(listOf())
        }
    }

    fun getData(name: String) {
        disposable?.dispose()
        disposable = api.getPokemonData(name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { result: PokemonModel.PokemonData ->
                myPokemonData.postValue(result)
            }
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}